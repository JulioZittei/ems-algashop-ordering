package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.ShoppingCartPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({SpringDataAuditingConfig.class, HibernateConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        statements = {
                "DELETE FROM shopping_cart_item",
                "DELETE FROM shopping_cart",
                "DELETE FROM customer"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS
)
class ShoppingCartPersistenceEntityRepositoryIT {

    private final ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    private CustomerPersistenceEntity customerPersistenceEntity;

    @Autowired
    public ShoppingCartPersistenceEntityRepositoryIT(
            ShoppingCartPersistenceEntityRepository shoppingCartPersistenceEntityRepository,
            CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.shoppingCartPersistenceEntityRepository = shoppingCartPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    void setup() {
        if (!customerPersistenceEntityRepository.existsById(
                CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value())) {

            customerPersistenceEntity = customerPersistenceEntityRepository.save(
                    CustomerPersistenceEntityTestDataBuilder.aCustomer().build()
            );
        } else {
            customerPersistenceEntity = customerPersistenceEntityRepository.findById(
                    CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value()
            ).orElseThrow();
        }
    }

    @Test
    void shouldPersist() {
        ShoppingCartPersistenceEntity shoppingCart = ShoppingCartPersistenceEntityTestDataBuilder.existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();

        shoppingCartPersistenceEntityRepository.saveAndFlush(shoppingCart);

        assertThat(
                shoppingCartPersistenceEntityRepository.existsById(shoppingCart.getId())
        ).isTrue();
    }

    @Test
    void shouldFindByCustomerId() {
        ShoppingCartPersistenceEntity shoppingCart = ShoppingCartPersistenceEntityTestDataBuilder
                .existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();

        shoppingCartPersistenceEntityRepository.saveAndFlush(shoppingCart);

        var found = shoppingCartPersistenceEntityRepository
                .findByCustomer_Id(customerPersistenceEntity.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(shoppingCart.getId());
        assertThat(found.get().getCustomer().getId())
                .isEqualTo(customerPersistenceEntity.getId());
    }

    @Test
    void shouldReturnEmptyWhenCustomerHasNoShoppingCart() {
        var result = shoppingCartPersistenceEntityRepository
                .findByCustomer_Id(UUID.randomUUID());

        assertThat(result).isEmpty();
    }

    @Test
    void shouldSetAuditingValues() {
        ShoppingCartPersistenceEntity shoppingCart = ShoppingCartPersistenceEntityTestDataBuilder
                .existingShoppingCart()
                .customer(customerPersistenceEntity)
                .build();

        shoppingCart = shoppingCartPersistenceEntityRepository
                .saveAndFlush(shoppingCart);

        assertThat(shoppingCart.getCreatedByUserId()).isNotNull();
        assertThat(shoppingCart.getLasModifiedByUserId()).isNotNull();
        assertThat(shoppingCart.getLastModifiedAt()).isNotNull();
    }

    @Test
    void shouldCount() {
        assertThat(shoppingCartPersistenceEntityRepository.count()).isZero();
    }
}
