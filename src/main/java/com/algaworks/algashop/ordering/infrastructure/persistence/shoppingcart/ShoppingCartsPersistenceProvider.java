package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.VersionSynchronizer;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCarts;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShoppingCartsPersistenceProvider implements ShoppingCarts {

    private final ShoppingCartPersistenceEntityRepository persistenceRepository;
    private final ShoppingCartPersistenceEntityAssembler assembler;
    private final ShoppingCartPersistenceEntityDisassembler disassembler;
    private final VersionSynchronizer<ShoppingCart> versionSynchronizer;

    @Override
    public Optional<ShoppingCart> ofId(ShoppingCartId shoppingCartId) {
        return persistenceRepository.findById(shoppingCartId.value())
                .map(disassembler::toDomainEntity);
    }

    @Override
    public boolean exists(ShoppingCartId shoppingCartId) {
        return persistenceRepository.existsById(shoppingCartId.value());
    }

    @Override
    @Transactional(readOnly = false)
    public void add(ShoppingCart aggregateRoot) {
        UUID shoppingCartId = aggregateRoot.id().value();

        persistenceRepository.findById(shoppingCartId)
                .ifPresentOrElse(
                        persistenceEntity -> update(aggregateRoot, persistenceEntity),
                        ()-> insert(aggregateRoot)
                );
    }

    @Override
    public long count() {
        return persistenceRepository.count();
    }

    private void update(ShoppingCart aggregateRoot, ShoppingCartPersistenceEntity persistenceEntity) {
        persistenceEntity = assembler.merge(persistenceEntity, aggregateRoot);
        persistenceEntity = persistenceRepository.saveAndFlush(persistenceEntity);
        versionSynchronizer.synchronizeVersion(aggregateRoot, persistenceEntity.getVersion());
    }

    private void insert(ShoppingCart aggregateRoot) {
        ShoppingCartPersistenceEntity persistenceEntity = assembler.fromDomain(aggregateRoot);
        persistenceRepository.saveAndFlush(persistenceEntity);
        versionSynchronizer.synchronizeVersion(aggregateRoot, persistenceEntity.getVersion());
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(ShoppingCart shoppingCart) {
        this.persistenceRepository.deleteById(shoppingCart.id().value());
    }

    @Override
    @Transactional(readOnly = false)
    public void remove(ShoppingCartId shoppingCartId) {
        this.persistenceRepository.deleteById(shoppingCartId.value());
    }

    @Override
    public Optional<ShoppingCart> ofCustomer(CustomerId customerId) {
        return persistenceRepository.findByCustomer_Id(customerId.value())
                .map(disassembler::toDomainEntity);
    }
}