package com.algaworks.algashop.ordering.infrastructure.persistence.provider;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.config.beans.VersionSynchronizerConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        SpringDataAuditingConfig.class,
        HibernateConfig.class,
        VersionSynchronizerConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomersPersistenceProviderIT {

    private final CustomersPersistenceProvider persistenceProvider;
    private final CustomerPersistenceEntityRepository persistenceEntityRepository;

    @Autowired
    public CustomersPersistenceProviderIT(CustomersPersistenceProvider persistenceProvider,
                                          CustomerPersistenceEntityRepository persistenceEntityRepository) {
        this.persistenceProvider = persistenceProvider;
        this.persistenceEntityRepository = persistenceEntityRepository;
    }

    @Test
    void shouldUpdateAndKeepPersistenceEntityState() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        UUID customerId = customer.id().value();

        persistenceProvider.add(customer);
        CustomerPersistenceEntity persistenceEntity = persistenceEntityRepository.findById(customerId).orElseThrow();

        assertThat(persistenceEntity.getArchived()).isFalse();
        assertThat(persistenceEntity.getArchivedAt()).isNull();
        assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        assertThat(persistenceEntity.getLasModifiedByUserId()).isNotNull();

        customer = persistenceProvider.ofId(customer.id()).orElseThrow();
        customer.archive();
        persistenceProvider.add(customer);
        persistenceEntity = persistenceEntityRepository.findById(customerId).orElseThrow();

        assertThat(persistenceEntity.getArchived()).isTrue();
        assertThat(persistenceEntity.getArchivedAt()).isNotNull();
        assertThat(persistenceEntity.getCreatedByUserId()).isNotNull();
        assertThat(persistenceEntity.getLastModifiedAt()).isNotNull();
        assertThat(persistenceEntity.getLasModifiedByUserId()).isNotNull();
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldAddFindAndNotFail_whenHasNoTransaction() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        persistenceProvider.add(customer);
        Customer savedOrder = persistenceProvider.ofId(customer.id()).orElseThrow();

        assertThat(savedOrder).isNotNull();
    }
}