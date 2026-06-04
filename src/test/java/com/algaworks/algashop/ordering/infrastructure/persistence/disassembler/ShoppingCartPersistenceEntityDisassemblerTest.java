package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.entity.ShoppingCart;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ShoppingCartPersistenceEntityDisassemblerTest {

    private final ShoppingCartPersistenceEntityDisassembler disassembler =
            new ShoppingCartPersistenceEntityDisassembler();

    @Test
    void shouldConvertToDomainEntity() {
        ShoppingCartPersistenceEntity persistenceEntity =
                ShoppingCartPersistenceEntityTestDataBuilder
                        .existingShoppingCart()
                        .build();

        ShoppingCart shoppingCart =
                disassembler.toDomainEntity(persistenceEntity);

        assertThat(shoppingCart.id().value())
                .isEqualTo(persistenceEntity.getId());

        assertThat(shoppingCart.customerId().value())
                .isEqualTo(persistenceEntity.getCustomerId());

        assertThat(shoppingCart.totalAmount().value())
                .isEqualTo(persistenceEntity.getTotalAmount());

        assertThat(shoppingCart.totalItems().value())
                .isEqualTo(persistenceEntity.getTotalItems());

        assertThat(shoppingCart.createdAt())
                .isEqualTo(persistenceEntity.getCreatedAt());

        assertThat(shoppingCart.version())
                .isEqualTo(persistenceEntity.getVersion());

        Assertions.assertThat(shoppingCart.items())
                .hasSize(persistenceEntity.getItems().size());

        shoppingCart.items().forEach(item -> {
            var itemPersistence = persistenceEntity.getItems().stream()
                    .filter(entityItem -> item.id().value().equals(entityItem.getId()))
                    .findFirst()
                    .orElse(null);

            assertThat(item.id().value())
                    .isEqualTo(itemPersistence.getId());

            assertThat(item.shoppingCartId().value())
                    .isEqualTo(itemPersistence.getShoppingCartId());

            assertThat(item.productId().value())
                    .isEqualTo(itemPersistence.getProductId());

            assertThat(item.name().value())
                    .isEqualTo(itemPersistence.getName());

            assertThat(item.price().value())
                    .isEqualTo(itemPersistence.getPrice());

            assertThat(item.quantity().value())
                    .isEqualTo(itemPersistence.getQuantity());

            assertThat(item.isAvailable())
                    .isEqualTo(itemPersistence.getAvailable());

            assertThat(item.totalAmount().value())
                    .isEqualTo(itemPersistence.getTotalAmount());
        });
    }


    @Test
    void shouldConvertShoppingCartWithoutItems() {
        ShoppingCartPersistenceEntity persistenceEntity =
                ShoppingCartPersistenceEntityTestDataBuilder
                        .existingShoppingCart()
                        .items(java.util.Set.of())
                        .build();

        ShoppingCart shoppingCart =
                disassembler.toDomainEntity(persistenceEntity);

        Assertions.assertThat(shoppingCart.items()).isEmpty();

        assertThat(shoppingCart.id().value())
                .isEqualTo(persistenceEntity.getId());

        assertThat(shoppingCart.customerId().value())
                .isEqualTo(persistenceEntity.getCustomerId());

        assertThat(shoppingCart.totalAmount().value())
                .isEqualTo(persistenceEntity.getTotalAmount());

        assertThat(shoppingCart.totalItems().value())
                .isEqualTo(persistenceEntity.getTotalItems());
    }
}
