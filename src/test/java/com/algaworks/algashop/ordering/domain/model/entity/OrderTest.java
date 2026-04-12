package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.exception.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderItemId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

class OrderTest {

    @Test
    void shouldGenerateDraftOrder() {
        CustomerId customerId = new CustomerId();
        Order order = Order.draft(customerId);

        Assertions.assertThat(order.id()).isNotNull();
        Assertions.assertThat(order.customerId()).isEqualTo(customerId);
        Assertions.assertThat(order.totalAmount()).isEqualTo(Money.ZERO);
        Assertions.assertThat(order.totalItems()).isEqualTo(Quantity.ZERO);
        Assertions.assertThat(order.isDraft()).isTrue();
        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(order.placedAt()).isNull();
        Assertions.assertThat(order.paidAt()).isNull();
        Assertions.assertThat(order.readyAt()).isNull();
        Assertions.assertThat(order.canceledAt()).isNull();
        Assertions.assertThat(order.billing()).isNull();
        Assertions.assertThat(order.shipping()).isNull();
        Assertions.assertThat(order.paymentMethod()).isNull();
    }

    @Test
    void shouldAddItem() {
        Order order = Order.draft(new CustomerId());

        Product product = ProductTestDataBuilder.aProduct().build();
        Quantity quantity = new Quantity(1);

        order.addItem(product, quantity);

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items()).hasSize(1);
        Assertions.assertThat(order.totalAmount()).isEqualTo(product.price());
        Assertions.assertThat(order.totalItems()).isEqualTo(quantity);

        OrderItem orderItem = order.items().iterator().next();

        Assertions.assertThat(orderItem.id()).isNotNull();
        Assertions.assertThat(orderItem.orderId()).isEqualTo(order.id());
        Assertions.assertThat(orderItem.productId()).isEqualTo(product.id());
        Assertions.assertThat(orderItem.productName()).isEqualTo(product.name());
        Assertions.assertThat(orderItem.price()).isEqualTo(product.price());
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
    void givenPlacedOrder_whenChangePaymentMethod_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        var newPaymentMethod = PaymentMethod.CREDIT_CARD;

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.changePaymentMethod(newPaymentMethod));
    }

    @Test
    void givenDraftOrder_whenAddItem_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var totalItems = order.totalItems();
        var totalAmount = order.totalAmount();

        order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1));

        Assertions.assertThat(order.totalItems()).isGreaterThan(totalItems);
        Assertions.assertThat(order.totalAmount()).isGreaterThan(totalAmount);
    }

    @Test
    void givenPlacedOrder_whenAddItem_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        var totalItems = order.totalItems();
        var totalAmount = order.totalAmount();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.addItem(ProductTestDataBuilder.aProduct().build(), new Quantity(1)));

        Assertions.assertThat(order.totalItems()).isEqualTo(totalItems);
        Assertions.assertThat(order.totalAmount()).isEqualTo(totalAmount);
    }

    @Test
    void givenDraftOrder_whenRemoveItem_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var totalItems = order.totalItems();
        var totalAmount = order.totalAmount();

        OrderItemId orderItemId = order.items().iterator().next().id();

        order.removeItem(orderItemId);

        Assertions.assertThat(order.totalItems()).isLessThan(totalItems);
        Assertions.assertThat(order.totalAmount()).isLessThan(totalAmount);
    }

    @Test
    void givenPlacedOrder_whenRemoveItem_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        var totalItems = order.totalItems();
        var totalAmount = order.totalAmount();

        OrderItemId orderItemId = order.items().iterator().next().id();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() ->  order.removeItem(orderItemId));

        Assertions.assertThat(order.totalItems()).isEqualTo(totalItems);
        Assertions.assertThat(order.totalAmount()).isEqualTo(totalAmount);
    }

    @Test
    void givenDraftOrderAndOrderDoesNotContainOrderItem_whenRemoveItem_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.DRAFT).build();
        var totalItems = order.totalItems();
        var totalAmount = order.totalAmount();

        OrderItemId orderItemId = new OrderItemId();

        Assertions.assertThatExceptionOfType(OrderDoesNotContainOrderItemException.class)
                .isThrownBy(() ->  order.removeItem(orderItemId));

        Assertions.assertThat(order.totalItems()).isEqualTo(totalItems);
        Assertions.assertThat(order.totalAmount()).isEqualTo(totalAmount);
    }

    @Test
    void givenDraftOrder_whenChangeItemQuantity_shouldAllowChange() {
        Order order = Order.draft(new CustomerId());

        order.addItem(
                ProductTestDataBuilder.aProduct().build(),
                new Quantity(2));

        OrderItem orderItem = order.items().iterator().next();
        OrderItemId orderItemId = orderItem.id();
        Quantity newQuantity = new Quantity(orderItem.quantity().value() + 1);
        Money newTotalAmount = new Money(orderItem.price().value().multiply(BigDecimal.valueOf(newQuantity.value())));

        order.changeItemQuantity(orderItemId, newQuantity);

        Assertions.assertThat(order.items().iterator().next().quantity()).isEqualTo(newQuantity);
        Assertions.assertThat(order.items().iterator().next().totalAmount()).isEqualTo(newTotalAmount);
        Assertions.assertThat(order.totalItems()).isEqualTo(newQuantity);
    }

    @Test
    void givenPlacedOrder_whenChangeItemQuantity_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        var totalItems = order.totalItems();
        var totalAmount = order.totalAmount();

        OrderItem orderItem = order.items().iterator().next();
        OrderItemId orderItemId = orderItem.id();
        Quantity newQuantity = new Quantity(orderItem.quantity().value() + 1);
        Money newTotalAmount = new Money(orderItem.price().value().multiply(BigDecimal.valueOf(newQuantity.value())));

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.changeItemQuantity(orderItemId, newQuantity));

        Assertions.assertThat(order.totalItems()).isEqualTo(totalItems);
        Assertions.assertThat(order.totalAmount()).isEqualTo(totalAmount);
        Assertions.assertThat(order.items().iterator().next().quantity()).isNotEqualTo(newQuantity);
        Assertions.assertThat(order.items().iterator().next().totalAmount()).isNotEqualTo(newTotalAmount);
    }

    @Test
    void givenDraftOrder_whenChangeBilling_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newEmail = new Email("firstlast@email.com");
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

        var newBilling = Billing.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .email(newEmail)
                .document(newDocument)
                .address(newAddress)
                .build();

        order.changeBilling(newBilling);

        Assertions.assertThat(order.billing()).isEqualTo(newBilling);
    }

    @Test
    void givenPlacedOrder_whenChangeBilling_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newEmail = new Email("firstlast@email.com");
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

        var newBilling = Billing.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .email(newEmail)
                .document(newDocument)
                .address(newAddress)
                .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.changeBilling(newBilling));

        Assertions.assertThat(order.billing()).isNotEqualTo(newBilling);
    }

    @Test
    void givenDraftOrder_whenChangeShipping_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();
        var oldAmount = order.totalAmount();

        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");

        var newRecipient = Recipient.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .build();

        var newAddress = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );

        var newShippingCost = new Money("12.0");
        var newExpectedDeliveryDate = LocalDate.now().plusDays(7);

        var newShipping = Shipping.builder()
                .recipient(newRecipient)
                .address(newAddress)
                .expectedDate(newExpectedDeliveryDate)
                .cost(newShippingCost)
                .build();

        order.changeShipping(newShipping);

        Assertions.assertThat(order.shipping()).isEqualTo(newShipping);
        Assertions.assertThat(order.shipping().cost()).isEqualTo(newShippingCost);
        Assertions.assertThat(order.shipping().expectedDate()).isEqualTo(newExpectedDeliveryDate);
        Assertions.assertThat(order.totalAmount()).isNotEqualTo(oldAmount);
    }

    @Test
    void givenPlacedOrder_whenChangeShipping_shouldNotAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().status(OrderStatus.PLACED).build();
        var oldAmount = order.totalAmount();

        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");

        var newRecipient = Recipient.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .build();

        var newAddress = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );

        var newShippingCost = new Money("12.0");
        var newExpectedDeliveryDate = LocalDate.now().plusDays(2);

        var newShipping = Shipping.builder()
                .recipient(newRecipient)
                .address(newAddress)
                .expectedDate(newExpectedDeliveryDate)
                .cost(newShippingCost)
                .build();

        Assertions.assertThatExceptionOfType(OrderCannotBeEditedException.class)
                .isThrownBy(() -> order.changeShipping(newShipping));

        Assertions.assertThat(order.shipping()).isNotEqualTo(newShipping);
        Assertions.assertThat(order.shipping().cost()).isNotEqualTo(newShippingCost);
        Assertions.assertThat(order.shipping().expectedDate()).isNotEqualTo(newExpectedDeliveryDate);
        Assertions.assertThat(order.totalAmount()).isEqualTo(oldAmount);
    }

    @Test
    void givenDraftOrderAndExpectedDeliveryDateInThePast_whenChangeShipping_shouldNotAllowChange() {
        Order order = Order.draft(new CustomerId());
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");

        var recipient = Recipient.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .build();


        var newAddress = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );

        var newShippingCost = new Money("12.0");
        var newExpectedDeliveryDate = LocalDate.now().minusDays(1);

        var newShipping = Shipping.builder()
                .recipient(recipient)
                .address(newAddress)
                .expectedDate(newExpectedDeliveryDate)
                .cost(newShippingCost)
                .build();

        Assertions.assertThatExceptionOfType(OrderInvalidShippingDeliveryDateException.class)
                .isThrownBy(() -> order.changeShipping(newShipping));
    }

    @Test
    void givingDraftOrder_WhenMarkAsPlaced_shouldChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder().build();

        order.markAsPlaced();

        Assertions.assertThat(order.isPlaced()).isTrue();
        Assertions.assertThat(order.placedAt()).isBeforeOrEqualTo(OffsetDateTime.now());
    }

    @Test
    void givingDraftOrder_WhenMarkAsPaid_shouldNotChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                        .isThrownBy(order::markAsPaid);

        Assertions.assertThat(order.isPaid()).isFalse();
        Assertions.assertThat(order.paidAt()).isNull();
    }

    @Test
    void givingDraftOrder_WhenMarkAsPaid_shouldNotChangeToReady() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.isReady()).isFalse();
        Assertions.assertThat(order.readyAt()).isNull();
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
    void givingPlacedOrder_WhenMarkAsReady_shouldNotChangeToReady() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.isReady()).isFalse();
        Assertions.assertThat(order.readyAt()).isNull();
    }

    @Test
    void givingPlacedOrder_WhenMarkAsPlaced_shouldNotChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PLACED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsPlaced);

        Assertions.assertThat(order.isPlaced()).isTrue();
        Assertions.assertThat(order.placedAt()).isNotNull();
    }

    @Test
    void givingReadyOrder_WhenMarkAsPlaced_shouldNotChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.READY).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsPlaced);

        Assertions.assertThat(order.isPlaced()).isFalse();
        Assertions.assertThat(order.placedAt()).isNotNull();
    }

    @Test
    void givingReadyOrder_WhenMarkAsPaid_shouldNotChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.READY).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsPaid);

        Assertions.assertThat(order.isPaid()).isFalse();
        Assertions.assertThat(order.paidAt()).isNotNull();
    }

    @Test
    void givingReadyOrder_WhenMarkAsReady_shouldNotChangeToReady() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.READY).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsReady);

        Assertions.assertThat(order.isReady()).isTrue();
        Assertions.assertThat(order.paidAt()).isNotNull();
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
    void givingPaidOrder_WhenMarkAsPaid_shouldNotChangeToPaid() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsPaid);

        Assertions.assertThat(order.isPaid()).isTrue();
        Assertions.assertThat(order.paidAt()).isNotNull();
    }

    @Test
    void givingPaidOrder_WhenMarkAsPlaced_shouldNotChangeToPlaced() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.PAID).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsPlaced);

        Assertions.assertThat(order.isPlaced()).isFalse();
        Assertions.assertThat(order.placedAt()).isNotNull();
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
    void givingCanceledOrder_WhenMarkAsCanceled_shouldNotChangeToCanceled() {
        Order order = OrderTestDataBuilder.anOrder()
                .status(OrderStatus.CANCELED).build();

        Assertions.assertThatExceptionOfType(OrderStatusCannotBeChangedException.class)
                .isThrownBy(order::markAsCanceled);

        Assertions.assertThat(order.isCanceled()).isTrue();
        Assertions.assertThat(order.canceledAt()).isNotNull();
    }

    @Test
    void givingDraftOrderAndRequiredFieldsNotFilled_WhenMarkAsPlaced_shouldGenerateAnException() {
        Order order = Order.draft(new CustomerId());

        Assertions.assertThatExceptionOfType(OrderCannotBePlacedException.class)
                .isThrownBy(order::markAsPlaced);

        Assertions.assertThat(order.isPlaced()).isFalse();
        Assertions.assertThat(order.canceledAt()).isNull();
    }

    @Test
    void givenOrderDraft_whenTryToChangeShipping_shouldAllowChange() {
        Order order = OrderTestDataBuilder.anOrder().build();

        Assertions.assertThatExceptionOfType(ProductOutOfStockException.class)
                .isThrownBy(() -> order.addItem(
                        ProductTestDataBuilder.aProductUnavailable().build(),
                        new Quantity(2)));
    }
}