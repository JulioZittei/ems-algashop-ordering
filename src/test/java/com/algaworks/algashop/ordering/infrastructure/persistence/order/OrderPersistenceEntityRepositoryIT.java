package com.algaworks.algashop.ordering.infrastructure.persistence.order;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.HibernateConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityRepository;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@DataJpaTest
@Import({SpringDataAuditingConfig.class, HibernateConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = { "DELETE FROM order_item", "DELETE FROM \"order\"","DELETE FROM \"customer\"" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class OrderPersistenceEntityRepositoryIT {

    private final OrderPersistenceEntityRepository orderPersistenceEntityRepository;
    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;
    private CustomerPersistenceEntity customerPersistenceEntity;

    @Autowired
    public OrderPersistenceEntityRepositoryIT(OrderPersistenceEntityRepository orderPersistenceEntityRepository,
                                              CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.orderPersistenceEntityRepository = orderPersistenceEntityRepository;
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @BeforeEach
    void setup() {
        if(!customerPersistenceEntityRepository.existsById(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID.value())) {
            customerPersistenceEntity = customerPersistenceEntityRepository.save(CustomerPersistenceEntityTestDataBuilder.aCustomer().build());
        }
    }

    @Test
    void shouldPersist() {
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();
        orderPersistenceEntityRepository.saveAndFlush(orderPersistenceEntity);

        assertThat(orderPersistenceEntityRepository.existsById(orderPersistenceEntity.getId())).isTrue();
        OrderPersistenceEntity orderPersistenceSaved = orderPersistenceEntityRepository
                .findById(orderPersistenceEntity.getId()).orElseThrow();
        assertThat(orderPersistenceSaved.getItems()).isNotNull();
        Assertions.assertThat(orderPersistenceSaved.getItems()).isNotEmpty();

    }

    @Test
    void shouldCount() {
        long ordersCount = orderPersistenceEntityRepository.count();
        assertThat(ordersCount).isZero();
    }

    @Test
    void shouldSetAuditingValues() {
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .customer(customerPersistenceEntity)
                .build();
        orderPersistenceEntity = orderPersistenceEntityRepository.saveAndFlush(orderPersistenceEntity);

        assertThat(orderPersistenceEntityRepository.existsById(orderPersistenceEntity.getId())).isTrue();
        assertThat(orderPersistenceEntity.getCreatedByUserId()).isNotNull();
        assertThat(orderPersistenceEntity.getLasModifiedByUserId()).isNotNull();
        assertThat(orderPersistenceEntity.getLastModifiedAt()).isNotNull();
    }

}