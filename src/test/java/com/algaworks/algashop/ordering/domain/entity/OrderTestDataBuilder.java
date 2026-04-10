package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.*;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();
    private PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
    private Money shippingCost = new Money("10.00");
    private LocalDate expectedDeliveryDate = LocalDate.now().plusWeeks(1);
    private ShippingInfo shippingInfo = aShippingInfo();
    private BillingInfo billingInfo = aBillingInfo();


    private boolean withItems = true;
    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShipping(shippingInfo, shippingCost, expectedDeliveryDate);
        order.changeBilling(billingInfo);
        order.changePaymentMethod(paymentMethod);

        if(withItems) {
            order.addItem(
                    new ProductId(),
                    new ProductName("Notebook X11"),
                    new Money("3000.00"),
                    new Quantity(2));

            order.addItem(
                    new ProductId(),
                    new ProductName("Macbook Air M5"),
                    new Money("7000.00"),
                    new Quantity(1));
        }

        switch (status) {
            case PLACED -> order.markAsPlaced();
            case PAID -> {
                order.markAsPlaced();
                order.markAsPaid();
            }
            case READY -> {
                order.markAsPlaced();
                order.markAsPaid();
                order.markAsReady();
            }
            case CANCELED -> order.markAsCanceled();
            default -> {/* Do nothing  */ }
        }

        return order;
    }

    private BillingInfo aBillingInfo() {
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");
        var newAddress = anAddress();

        return BillingInfo.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .address(newAddress)
                .build();
    }

    private ShippingInfo aShippingInfo() {
        var newFullName = new FullName("First Name", "Last Name");
        var newPhone = new Phone("12345678");
        var newDocument = new Document("12345678901");
        var newAddress = anAddress();

        return ShippingInfo.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .address(newAddress)
                .build();
    }

    private Address anAddress() {
        return new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                new ZipCode("12345")
        );
    }

    public OrderTestDataBuilder customerId(CustomerId customerId) {
        this.customerId = customerId;
        return this;
    }

    public OrderTestDataBuilder paymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    public OrderTestDataBuilder shippingCost(Money shippingCost) {
        this.shippingCost = shippingCost;
        return this;
    }

    public OrderTestDataBuilder expectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
        return this;
    }

    public OrderTestDataBuilder shippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
        return this;
    }

    public OrderTestDataBuilder billingInfo(BillingInfo billingInfo) {
        this.billingInfo = billingInfo;
        return this;
    }

    public OrderTestDataBuilder withItems(boolean withItems) {
        this.withItems = withItems;
        return this;
    }

    public OrderTestDataBuilder status(OrderStatus status) {
        this.status = status;
        return this;
    }
}
