package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import com.algaworks.algashop.ordering.domain.model.VersionSynchronizer;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.OrderPersistenceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrdersPersistenceProvider implements Orders {

    private final OrderPersistenceEntityRepository persistenceRepository;
    private final OrderPersistenceEntityAssembler assembler;
    private final OrderPersistenceEntityDisassembler disassembler;
    private final VersionSynchronizer<Order> versionSynchronizer;

    @Override
    public Optional<Order> ofId(OrderId orderId) {
        return persistenceRepository.findById(orderId.value().toLong())
                .map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(OrderId orderId) {
        return persistenceRepository.existsById(orderId.value().toLong());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(Order aggregateRoot) {
        long orderId = aggregateRoot.id().value().toLong();
        persistenceRepository.findById(orderId)
                .ifPresentOrElse(
                        persistenceEntity -> update(aggregateRoot, persistenceEntity),
                        () -> insert(aggregateRoot)
                );
    }

    @Override
    public long count() {
        return persistenceRepository.count();
    }


    @Override
    public List<Order> placedByCustomerInYear(CustomerId customerId, Year year) {
        return persistenceRepository.placedByCustomerInYear(customerId.value(), year.getValue())
                .stream().map(disassembler::toDomainEntity).toList();
    }

    @Override
    public long salesQuantityByCustomerInYear(CustomerId customerId, Year year) {
        return persistenceRepository.salesQuantityByCustomerInYear(customerId.value(), year.getValue());
    }

    @Override
    public Money totalSoldForCustomer(CustomerId customerId) {
        return new Money(persistenceRepository.totalSoldForCustomer(customerId.value()));
    }

    private void update(Order aggregateRoot, OrderPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        versionSynchronizer.synchronizeVersion(aggregateRoot, persistenceEntity.getVersion());
    }

    private void insert(Order aggregateRoot) {
        OrderPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        versionSynchronizer.synchronizeVersion(aggregateRoot, persistenceEntity.getVersion());
    }
}
