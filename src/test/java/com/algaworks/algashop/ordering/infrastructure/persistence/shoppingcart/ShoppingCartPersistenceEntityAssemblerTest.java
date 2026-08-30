package com.algaworks.algashop.ordering.infrastructure.persistence.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCart;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartPersistenceEntityAssemblerTest {

    @InjectMocks
    private ShoppingCartPersistenceEntityAssembler assembler;

    @Mock
    private CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    @BeforeEach
    void setup() {
        when(customerPersistenceEntityRepository.getReferenceById(Mockito.any(UUID.class)))
                .then(invocation -> {
                    UUID customerId = invocation.getArgument(0, UUID.class);
                    return CustomerPersistenceEntityTestDataBuilder.aCustomer()
                            .id(customerId)
                            .build();
                });
    }

    @Test
    void shouldConvertFromDomain() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        ShoppingCartPersistenceEntity persistenceEntity =
                assembler.fromDomain(shoppingCart);

        assertThat(persistenceEntity.getId())
                .isEqualTo(shoppingCart.id().value());

        assertThat(persistenceEntity.getCustomer().getId())
                .isEqualTo(shoppingCart.customerId().value());

        assertThat(persistenceEntity.getTotalAmount())
                .isEqualTo(shoppingCart.totalAmount().value());

        assertThat(persistenceEntity.getTotalItems())
                .isEqualTo(shoppingCart.totalItems().value());

        assertThat(persistenceEntity.getCreatedAt())
                .isEqualTo(shoppingCart.createdAt());

        assertThat(persistenceEntity.getVersion())
                .isEqualTo(shoppingCart.version());

        Assertions.assertThat(persistenceEntity.getItems())
                .hasSameSizeAs(shoppingCart.items());
    }

    @Test
    void shouldMerge() {
        ShoppingCart shoppingCart = ShoppingCartTestDataBuilder.aShoppingCart().build();

        ShoppingCartPersistenceEntity persistenceEntity =
                assembler.merge(new ShoppingCartPersistenceEntity(), shoppingCart);

        assertThat(persistenceEntity.getId())
                .isEqualTo(shoppingCart.id().value());

        assertThat(persistenceEntity.getCustomer().getId())
                .isEqualTo(shoppingCart.customerId().value());

        assertThat(persistenceEntity.getTotalAmount())
                .isEqualTo(shoppingCart.totalAmount().value());

        assertThat(persistenceEntity.getTotalItems())
                .isEqualTo(shoppingCart.totalItems().value());

        assertThat(persistenceEntity.getCreatedAt())
                .isEqualTo(shoppingCart.createdAt());

        assertThat(persistenceEntity.getVersion())
                .isEqualTo(shoppingCart.version());

        Assertions.assertThat(persistenceEntity.getItems())
                .hasSameSizeAs(shoppingCart.items());
    }

    @Test
    void givenShoppingCartWithNoItems_shouldRemovePersistenceEntityItems() {
        ShoppingCart shoppingCart =
                ShoppingCartTestDataBuilder.aShoppingCart()
                        .withItems(false)
                        .build();

        ShoppingCartPersistenceEntity persistenceEntity =
                ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                        .build();

        Assertions.assertThat(shoppingCart.items()).isEmpty();
        Assertions.assertThat(persistenceEntity.getItems()).isNotEmpty();

        assembler.merge(persistenceEntity, shoppingCart);

        Assertions.assertThat(persistenceEntity.getItems()).isEmpty();
    }

    @Test
    void givenShoppingCartWithItems_shouldAddToPersistenceEntityItems() {
        ShoppingCart shoppingCart =
                ShoppingCartTestDataBuilder.aShoppingCart()
                        .withItems(true)
                        .build();

        ShoppingCartPersistenceEntity persistenceEntity =
                ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                        .items(new HashSet<>())
                        .build();

        Assertions.assertThat(shoppingCart.items()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems()).isEmpty();

        assembler.merge(persistenceEntity, shoppingCart);

        Assertions.assertThat(persistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(persistenceEntity.getItems())
                .hasSameSizeAs(shoppingCart.items());
    }

    @Test
    void givenShoppingCartWithItems_whenMerge_shouldMergeCorrectly() {
        ShoppingCart shoppingCart =
                ShoppingCartTestDataBuilder.aShoppingCart()
                        .withItems(true)
                        .build();

        Set<ShoppingCartItemPersistenceEntity> itemPersistenceEntities =
                shoppingCart.items().stream()
                        .map(item -> {
                            ShoppingCartItemPersistenceEntity entity =
                                    new ShoppingCartItemPersistenceEntity();

                            entity.setId(item.id().value());
                            entity.setProductId(item.productId().value());
                            entity.setName(item.name().value());
                            entity.setPrice(item.price().value());
                            entity.setQuantity(item.quantity().value());
                            entity.setAvailable(item.isAvailable());
                            entity.setTotalAmount(item.totalAmount().value());

                            return entity;
                        })
                        .collect(Collectors.toSet());

        ShoppingCartPersistenceEntity persistenceEntity =
                ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                        .items(itemPersistenceEntities)
                        .build();

        Assertions.assertThat(shoppingCart.items()).hasSize(2);
        Assertions.assertThat(persistenceEntity.getItems()).hasSize(2);

        shoppingCart.removeItem(
                shoppingCart.items().iterator().next().id()
        );

        Assertions.assertThat(shoppingCart.items()).hasSize(1);
        Assertions.assertThat(persistenceEntity.getItems()).hasSize(2);

        assembler.merge(persistenceEntity, shoppingCart);

        Assertions.assertThat(persistenceEntity.getItems())
                .hasSameSizeAs(shoppingCart.items());
    }
}
