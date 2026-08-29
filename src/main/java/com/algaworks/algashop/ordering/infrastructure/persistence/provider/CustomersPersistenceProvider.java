package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.VersionSynchronizer;
import com.algaworks.algashop.ordering.domain.model.commons.Email;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomersPersistenceProvider implements Customers {

    private final CustomerPersistenceEntityRepository persistenceRepository;
    private final CustomerPersistenceEntityAssembler assembler;
    private final CustomerPersistenceEntityDisassembler disassembler;
    private final VersionSynchronizer<Customer> versionSynchronizer;

    @Override
    public Optional<Customer> ofId(CustomerId customerId) {
        return persistenceRepository.findById(customerId.value())
                .map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(CustomerId customerId) {
        return persistenceRepository.existsById(customerId.value());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Customer aggregateRoot) {
        UUID customerId = aggregateRoot.id().value();

        persistenceRepository.findById(customerId)
                .ifPresentOrElse(
                        persistenceEntity -> update(aggregateRoot, persistenceEntity),
                        ()-> insert(aggregateRoot)
                );
    }

    @Override
    public long count() {
        return persistenceRepository.count();
    }

    @Override
    public Optional<Customer> ofEmail(Email email) {
        return persistenceRepository.findByEmail(email.value())
                .map(disassembler::toDomainEntity);
    }

    @Override
    public boolean isEmailUnique(Email email, CustomerId exceptCustomerId) {
        return !persistenceRepository.existsByEmailAndIdNot(email.value(), exceptCustomerId.value());
    }

    private void update(Customer aggregateRoot, CustomerPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        versionSynchronizer.synchronizeVersion(aggregateRoot, persistenceEntity.getVersion());
    }

    private void insert(Customer aggregateRoot) {
        CustomerPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        versionSynchronizer.synchronizeVersion(aggregateRoot, persistenceEntity.getVersion());
    }
}
