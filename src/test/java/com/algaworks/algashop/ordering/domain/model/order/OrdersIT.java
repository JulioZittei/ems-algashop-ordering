package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.domain.model.customer.Customers;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.infrastructure.beans.VersionSynchronizerConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntityAssembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.HibernateConfig;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrderPersistenceEntityDisassembler;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomersPersistenceProvider;
import com.algaworks.algashop.ordering.infrastructure.persistence.order.OrdersPersistenceProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({
        OrdersPersistenceProvider.class,
        CustomersPersistenceProvider.class,
        OrderPersistenceEntityAssembler.class,
        CustomerPersistenceEntityAssembler.class,
        OrderPersistenceEntityDisassembler.class,
        CustomerPersistenceEntityDisassembler.class,
        VersionSynchronizerConfig.class,
        HibernateConfig.class
})
@Sql(statements = { "DELETE FROM order_item", "DELETE FROM \"order\"" })
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrdersIT {

    private final Orders orders;
    private final Customers customers;
    private final TransactionTemplate newTransaction;

    @Autowired
    public OrdersIT(Orders orders, Customers customers, PlatformTransactionManager transactionManager) {
        this.orders = orders;
        this.customers = customers;
        this.newTransaction = new TransactionTemplate(transactionManager);
        this.newTransaction.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    }

    @BeforeEach
    void setup() {
        if(!customers.exists(CustomerTestDataBuilder.DEFAULT_CUSTOMER_ID)) {
            customers.add(CustomerTestDataBuilder.existingArchived().build());
        }
    }

    @Test
    void shouldPersistAndFind() {
        Order order = OrderTestDataBuilder.anOrder().build();

        orders.add(order);
        Optional<Order> possibleOrder = orders.ofId(order.id());

        Assertions.assertThat(possibleOrder).isPresent();
        Order addedOrder = possibleOrder.get();
        assertThat(addedOrder.id()).isEqualTo(order.id());
        assertThat(addedOrder.customerId()).isEqualTo(order.customerId());
        assertThat(addedOrder.billing()).isEqualTo(order.billing());
        assertThat(addedOrder.shipping()).isEqualTo(order.shipping());
        assertThat(addedOrder.totalAmount()).isEqualTo(order.totalAmount());
        assertThat(addedOrder.totalItems()).isEqualTo(order.totalItems());
        assertThat(addedOrder.status()).isEqualTo(order.status());
        assertThat(addedOrder.paymentMethod()).isEqualTo(order.paymentMethod());
        assertThat(addedOrder.items()).isEqualTo(order.items());
        assertThat(addedOrder.placedAt()).isEqualTo(order.placedAt());
        assertThat(addedOrder.paidAt()).isEqualTo(order.paidAt());
        assertThat(addedOrder.readyAt()).isEqualTo(order.readyAt());
        assertThat(addedOrder.canceledAt()).isEqualTo(order.canceledAt());
        assertThat(addedOrder.version()).isEqualTo(order.version());
    }

    @Test
    void shouldUpdateExistingOrder() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        orders.add(order);
        order = orders.ofId(order.id()).orElseThrow();
        Long oldVersion = order.version();
        order.markAsPaid();
        orders.add(order);
        order = orders.ofId(order.id()).orElseThrow();

        assertThat(order.isPaid()).isTrue();
        assertThat(order.placedAt()).isNotNull();
        assertThat(order.paidAt()).isNotNull();
        assertThat(order.version()).isNotEqualTo(oldVersion);
    }

    @Test
    void shouldCountExistingOrders() {
        assertThat(orders.count()).isZero();

        Order order1 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        Order order2 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        orders.add(order1);
        orders.add(order2);

        assertThat(orders.count()).isEqualTo(2);
    }

    @Test
    void shouldReturnIfOrderExists() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        assertThat(orders.exists(order.id())).isFalse();

        orders.add(order);

        assertThat(orders.exists(order.id())).isTrue();
    }

    @Test
    void shouldListExistingOrdersByYear() {
        Order order1 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        Order order2 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        Order order3 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.CANCELED)
                .build();

        Order order4 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.DRAFT)
                .build();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);

        List<Order> orderList = orders.placedByCustomerInYear(order1.customerId(), Year.now());

        assertThat(orderList).isNotNull();
        Assertions.assertThat(orderList).isNotEmpty();
        Assertions.assertThat(orderList).hasSize(2);

        List<Order> orderListLastYear = orders.placedByCustomerInYear(order1.customerId(), Year.now().minusYears(1));
        assertThat(orderListLastYear).isNotNull();
        Assertions.assertThat(orderListLastYear).isEmpty();

        List<Order> orderListForUnknownCustomer = orders.placedByCustomerInYear(new CustomerId(), Year.now().minusYears(1));
        assertThat(orderListForUnknownCustomer).isNotNull();
        Assertions.assertThat(orderListForUnknownCustomer).isEmpty();
    }

    @Test
    void shouldReturnTotalAmountSoldByCustomer() {
        Order order1 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID)
                .build();

        Order order2 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID)
                .build();

        Order order3 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.CANCELED)
                .build();

        Order order4 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);

        var expectedTotalAmount = order1.totalAmount().add(order2.totalAmount());

        var totalAmount = orders.totalSoldForCustomer(order1.customerId());
        assertThat(totalAmount.value()).isEqualTo(expectedTotalAmount.value());

        totalAmount = orders.totalSoldForCustomer(new CustomerId());
        assertThat(totalAmount).isEqualTo(Money.ZERO);
    }

    @Test
    void shouldReturnTotalSalesQuantityByCustomerInYear() {
        Order order1 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID)
                .build();

        Order order2 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID)
                .build();

        Order order3 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.CANCELED)
                .build();

        Order order4 = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
        orders.add(order4);

        var totalSalesQuantity = orders.salesQuantityByCustomerInYear(order1.customerId(), Year.now());
        assertThat(totalSalesQuantity).isEqualTo(2L);

        totalSalesQuantity = orders.salesQuantityByCustomerInYear(order1.customerId(), Year.now().minusYears(1));
        assertThat(totalSalesQuantity).isZero();

        totalSalesQuantity = orders.salesQuantityByCustomerInYear(new CustomerId(), Year.now());
        assertThat(totalSalesQuantity).isZero();
    }

    @Test
    void shouldNotAllowStaleUpdates() {
        // T0: insere o pedido em transação própria
        OrderId orderId = inNewTransaction(() -> {
            Customer customer = CustomerTestDataBuilder.brandNew().build();
            customers.add(customer);
            Order order = OrderTestDataBuilder.anOrder()
                    .customerId(customer.id())
                    .status(OrderStatus.PLACED).build();
            orders.add(order);
            return order.id();
        });

        Assertions.assertThatExceptionOfType(ObjectOptimisticLockingFailureException.class)
                .isThrownBy(() -> inNewTransaction(() -> {
                    // T1: carrega o pedido em sua própria transação
                    Order orderT1 = orders.ofId(orderId).orElseThrow();

                    // T2: em outra transação separada, salva primeiro
                    inNewTransaction(() -> {
                        Order orderT2 = orders.ofId(orderId).orElseThrow();
                        orderT2.markAsPaid();
                        orders.add(orderT2);
                    });

                    // T1 tenta salvar com versão obsoleta
                    orderT1.markAsCanceled();
                    orders.add(orderT1);
                }));

        // Verifica que a atualização de T2 prevaleceu
        Order savedOrder = orders.ofId(orderId).orElseThrow();
        Assertions.assertThat(savedOrder.canceledAt()).isNull();
        Assertions.assertThat(savedOrder.paidAt()).isNotNull();
    }

    private <T> T inNewTransaction(Supplier<T> callback) {
        return newTransaction.execute(status -> callback.get());
    }

    private void inNewTransaction(Runnable callback) {
        newTransaction.executeWithoutResult(status -> callback.run());
    }
}