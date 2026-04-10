package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.OrderInvalidShippingDeliveryDateException;
import com.algaworks.algashop.ordering.domain.exception.OrderStatusCannotBeChangedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

class OrderTest {

    @Test
    void shouldGenerate() {
        Order order = Order.draft(new CustomerId());
    }

    @Test
    void shouldAddItem() {
        Order order = Order.draft(new CustomerId());

        ProductId productId = new ProductId();
        ProductName productName = new ProductName("Novo produto");
        Money price = new Money("10.0");
        Quantity quantity = new Quantity(1);

        order.addItem(
                productId,
                productName,
                price,
                quantity);


        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items()).hasSize(1);
        Assertions.assertThat(order.totalAmount()).isEqualTo(price);
        Assertions.assertThat(order.totalItems()).isEqualTo(quantity);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertThat(orderItem.id()).isNotNull();
        Assertions.assertThat(orderItem.orderId()).isEqualTo(order.id());
        Assertions.assertThat(orderItem.productId()).isEqualTo(productId);
        Assertions.assertThat(orderItem.productName()).isEqualTo(productName);
        Assertions.assertThat(orderItem.price()).isEqualTo(price);
        Assertions.assertThat(orderItem.quantity()).isEqualTo(quantity);
    }

    @Test
    void shouldGenerateExceptionWhenTryToChangeItemSet() {
        Order order = OrderTestDataBuilder.anOrder()
                .withItems(true)
                .build();

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items()).hasSize(2);

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

    @Test
    void shouldGenerateExceptionWhenTryToChangeToInapplicableStatus() {
        Order order = OrderTestDataBuilder.anOrder()
                .withItems(true)
                .build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsPaid);
    }

    @Test
    void shouldChangeToApplicableStatus() {
        Order order = OrderTestDataBuilder.anOrder()
                .withItems(true)
                .build();

        Assertions.assertThatNoException()
                .isThrownBy(order::markAsPlaced);

        Assertions.assertThat(order.isPlaced()).isTrue();
    }

    @Test
    void givenDraftOrder_whenChangePaymentMethod_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var newPaymentMethod = PaymentMethod.CREDIT_CARD;
        order.changePaymentMethod(newPaymentMethod);

        Assertions.assertThat(order.paymentMethod()).isEqualTo(newPaymentMethod);
    }

    @Test
    void givenDraftOrder_whenChangeBilling_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");
        var newAddress = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );

        var newBilling = BillingInfo.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .address(newAddress)
                        .build();

        order.changeBilling(newBilling);

        Assertions.assertThat(order.billing()).isEqualTo(newBilling);
    }

    @Test
    void givenDraftOrder_whenChangeShipping_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var oldAmount = order.totalAmount();

        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");
        var newAddress = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );

        var newShipping = ShippingInfo.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .address(newAddress)
                .build();

        var newShippingCost = new Money("12.0");
        var newExpectedDeliveryDate = LocalDate.now().plusDays(7);

        order.changeShipping(newShipping, newShippingCost, newExpectedDeliveryDate);

        Assertions.assertThat(order.shipping()).isEqualTo(newShipping);
        Assertions.assertThat(order.shippingCost()).isEqualTo(newShippingCost);
        Assertions.assertThat(order.expectedDeliveryDate()).isEqualTo(newExpectedDeliveryDate);
        Assertions.assertThat(order.totalAmount()).isNotEqualTo(oldAmount);
    }

    @Test
    void givenDraftOrderAndExpectedDeliveryDateInThePast_whenChangeShipping_shouldNotAllowChange() {
        Order order = Order.draft(new CustomerId());
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");
        var newAddress = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );

        var newShipping = ShippingInfo.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .address(newAddress)
                .build();

        var newShippingCost = new Money("12.0");
        var newExpectedDeliveryDate = LocalDate.now().minusDays(1);

        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(()-> order.changeShipping(newShipping, newShippingCost, newExpectedDeliveryDate));
    }

    @Test
    void givingDraftOrder_WhenMarkAsPlaced_shouldChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();

        order.markAsPlaced();

        Assertions.assertThat(order.isPlaced()).isTrue();
        Assertions.assertThat(order.placedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingPlacedOrder_WhenMarkAsPaid_shouldChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        order.markAsPaid();

        Assertions.assertThat(order.isPaid()).isTrue();
        Assertions.assertThat(order.paidAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingPaidOrder_WhenMarkAsReady_shouldChangeToReady() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID)
                .build();

        order.markAsReady();

        Assertions.assertThat(order.isReady()).isTrue();
        Assertions.assertThat(order.readyAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingReadyOrder_WhenMarkAsCanceled_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.READY)
                .build();

        order.markAsCanceled();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingDraftOrder_WhenMarkAsCanceled_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder()
                .build();

        order.markAsCanceled();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingPlacedtOrder_WhenMarkAsCanceled_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED)
                .build();

        order.markAsCanceled();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingPaidtOrder_WhenMarkAsCanceled_shouldChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID)
                .build();

        order.markAsCanceled();

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingDraftOrderAndRequiredFieldsNotFilled_WhenMarkAsPlaced_shouldGenerateAnException() {
        Order order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(NullPointerException.class)
                        .isThrownBy(order::markAsPlaced);

        Assertions.assertThat(order.isPlaced()).isFalse();
        Assertions.assertThat(order.canceledAt()).isNull();
    }
}