package com.algaworks.algashop.ordering.infrastructure.persistence.repository;

import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(statements = { "DELETE FROM order_item", "DELETE FROM \"order\"","DELETE FROM \"customer\"" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Import({SpringDataAuditingConfig.class, HibernateConfig.class})
class CustomerPersistenceEntityRepositoryIT {

    private final CustomerPersistenceEntityRepository customerPersistenceEntityRepository;

    @Autowired
    public CustomerPersistenceEntityRepositoryIT(CustomerPersistenceEntityRepository customerPersistenceEntityRepository) {
        this.customerPersistenceEntityRepository = customerPersistenceEntityRepository;
    }

    @Test
    void shouldPersist() {
        CustomerPersistenceEntity customerPersistenceEntity = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();
        customerPersistenceEntityRepository.saveAndFlush(customerPersistenceEntity);

        assertThat(customerPersistenceEntityRepository.existsById(customerPersistenceEntity.getId())).isTrue();
        CustomerPersistenceEntity customerPersistenceEntitySaved = customerPersistenceEntityRepository
                .findById(customerPersistenceEntity.getId()).orElseThrow();
        assertThat(customerPersistenceEntitySaved).isNotNull();
        assertThat(customerPersistenceEntitySaved.getId()).isNotNull();
        assertThat(customerPersistenceEntitySaved.getAddress()).isNotNull();
    }

    @Test
    void shouldCount() {
        long customersCount = customerPersistenceEntityRepository.count();
        assertThat(customersCount).isZero();
    }

    @Test
    void shouldSetAuditingValues() {
        CustomerPersistenceEntity customerPersistenceEntity = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();
        customerPersistenceEntity = customerPersistenceEntityRepository.saveAndFlush(customerPersistenceEntity);

        assertThat(customerPersistenceEntityRepository.existsById(customerPersistenceEntity.getId())).isTrue();
        assertThat(customerPersistenceEntity.getCreatedByUserId()).isNotNull();
        assertThat(customerPersistenceEntity.getLasModifiedByUserId()).isNotNull();
        assertThat(customerPersistenceEntity.getLastModifiedAt()).isNotNull();
    }

}