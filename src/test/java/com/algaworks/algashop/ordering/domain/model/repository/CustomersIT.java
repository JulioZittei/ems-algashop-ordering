package com.algaworks.algashop.ordering.domain.model.repository;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.valueobject.Email;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.config.beans.VersionSynchronizerConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.assembler.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.config.HibernateConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.disassembler.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.provider.CustomersPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Sql(statements = { "DELETE FROM order_item", "DELETE FROM \"order\"", "DELETE FROM \"customer\"" })
@Import({
        CustomersPersistenceProvider.class,
        CustomerPersistenceEntityAssembler.class,
        CustomerPersistenceEntityDisassembler.class,
        VersionSynchronizerConfig.class,
        HibernateConfig.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomersIT {

    private final Customers customers;
    private final TransactionTemplate newTransaction;

    @Autowired
    public CustomersIT(Customers customers, PlatformTransactionManager transactionManager) {
        this.customers = customers;
        this.newTransaction = new TransactionTemplate(transactionManager);
        this.newTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @Test
    void shouldPersistAndFind() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        customers.add(customer);
        Optional<Customer> possibleCustomer = customers.ofId(customer.id());

        Assertions.assertThat(possibleCustomer).isPresent();
        Customer addedCustomer = possibleCustomer.get();
        assertThat(addedCustomer.id()).isEqualTo(customer.id());
        assertThat(addedCustomer.fullName()).isEqualTo(customer.fullName());
        assertThat(addedCustomer.document()).isEqualTo(customer.document());
        assertThat(addedCustomer.phone()).isEqualTo(customer.phone());
        assertThat(addedCustomer.email()).isEqualTo(customer.email());
        assertThat(addedCustomer.birthDate()).isEqualTo(customer.birthDate());
        assertThat(addedCustomer.isPromotionNotificationsAllowed()).isEqualTo(customer.isPromotionNotificationsAllowed());
        assertThat(addedCustomer.isArchived()).isEqualTo(customer.isArchived());
        assertThat(addedCustomer.archivedAt()).isEqualTo(customer.archivedAt());
        assertThat(addedCustomer.version()).isEqualTo(customer.version());
    }

    @Test
    void shouldUpdateExistingCustomer() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        customers.add(customer);
        customer = customers.ofId(customer.id()).orElseThrow();
        Long oldVersion = customer.version();
        customer.archive();
        customers.add(customer);
        customer = customers.ofId(customer.id()).orElseThrow();

        assertThat(customer.isArchived()).isTrue();
        assertThat(customer.archivedAt()).isNotNull();
        assertThat(customer.version()).isNotEqualTo(oldVersion);
    }

    @Test
    void shouldCountExistingCustomers() {
        assertThat(customers.count()).isZero();

        Customer customer1 = CustomerTestDataBuilder.brandNew().build();

        Customer customer2 = CustomerTestDataBuilder.brandNew().build();

        customers.add(customer1);
        customers.add(customer2);

        assertThat(customers.count()).isEqualTo(2);
    }

    @Test
    void shouldReturnIfCustomerExists() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        assertThat(customers.exists(customer.id())).isFalse();

        customers.add(customer);

        assertThat(customers.exists(customer.id())).isTrue();
    }

    @Test
    void shouldFindCustomerByEmail() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        assertThat(customers.exists(customer.id())).isFalse();

        customers.add(customer);
        Customer savedCustomer = customers.ofEmail(customer.email()).orElseThrow();
        assertThat(customers.exists(customer.id())).isTrue();
        assertThat(savedCustomer.id()).isEqualTo(customer.id());
        assertThat(savedCustomer.email()).isEqualTo(customer.email());
    }

    @Test
    void shouldReturnIfEmailIsInUse() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();
        customers.add(customer);

        assertThat(customers.isEmailUnique(customer.email(), customer.id())).isTrue();
        assertThat(customers.isEmailUnique(customer.email(), new CustomerId())).isFalse();
        assertThat(customers.isEmailUnique(new Email(UUID.randomUUID() + "@email.com"), new CustomerId())).isTrue();
    }

    @Test
    void shouldNotFindIfCustomerNotExistsWithEmail() {
        var possibleCustomer = customers.ofEmail(new Email(UUID.randomUUID() + "@email.com"));
        assertThat(possibleCustomer).isNotPresent();
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        // T0: insere o cliente em transação própria
        CustomerId customerId = inNewTransaction(() -> {
            Customer customer = CustomerTestDataBuilder.brandNew().build();
            customers.add(customer);
            return customer.id();
        });

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> inNewTransaction(() -> {
                    // T1: carrega o cliente em sua própria transação
                    Customer customerT1 = customers.ofId(customerId).orElseThrow();

                    // T2: em outra transação separada, salva primeiro
                    inNewTransaction(() -> {
                        Customer customerT2 = customers.ofId(customerId).orElseThrow();
                        customerT2.archive();
                        customers.add(customerT2);
                    });

                    // T1 tenta salvar com versão obsoleta
                    customerT1.enablePromotionNotifications();
                    customers.add(customerT1);
                }));

        // Verifica que a atualização de T2 prevaleceu
        Customer savedCustomer = customers.ofId(customerId).orElseThrow();
        Assertions.assertThat(savedCustomer.isPromotionNotificationsAllowed()).isFalse();
        Assertions.assertThat(savedCustomer.isArchived()).isTrue();
        Assertions.assertThat(savedCustomer.archivedAt()).isNotNull();
    }

    private <T> T inNewTransaction(Supplier<T> callback) {
        return newTransaction.execute(status -> callback.get());
    }

    private void inNewTransaction(Runnable callback) {
        newTransaction.executeWithoutResult(status -> callback.run());
    }
}