package com.algaworks.algashop.ordering.domain.model.entity;

import com.algaworks.algashop.ordering.domain.model.valueobject.*;
import com.algaworks.algashop.ordering.domain.model.valueobject.id.CustomerId;

import java.time.LocalDate;

public class OrderTestDataBuilder {

    private CustomerId customerId = new CustomerId();
    private PaymentMethod paymentMethod = PaymentMethod.CREDIT_CARD;
    private Shipping shipping = aShipping();
    private Billing billing = aBilling();


    private boolean withItems = true;
    private OrderStatus status = OrderStatus.DRAFT;

    private OrderTestDataBuilder() {
    }

    public static OrderTestDataBuilder anOrder() {
        return new OrderTestDataBuilder();
    }

    public Order build() {
        Order order = Order.draft(customerId);
        order.changeShipping(shipping);
        order.changeBilling(billing);
        order.changePaymentMethod(paymentMethod);

        if(withItems) {
            order.addItem(
                    ProductTestDataBuilder.aProduct()
                            .name(new ProductName("Notebook X11"))
                            .price(new Money("3000.00"))
                            .build(),
                    new Quantity(2));

            order.addItem(
                   ProductTestDataBuilder.aProduct().build(),
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

    public static Billing aBilling() {
        var newFullName = new FullName("Jhon", "Doe");
        var newPhone = new Phone("54-454-1145");
        var newEmail = new Email("jhondoe@email.com");
        var newDocument = new Document("552-11-4335");
        var newAddress = anAddress();

        return Billing.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .email(newEmail)
                .address(newAddress)
                .build();
    }

    public static Billing anAltBilling() {
        var newFullName = new FullName("Mary", "Jones");
        var newPhone = new Phone("54-454-1144");
        var newEmail = new Email("maryjones@email.com");
        var newDocument = new Document("552-11-4333");
        var newAddress = anAddress();

        return Billing.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .email(newEmail)
                .address(newAddress)
                .build();
    }

    public static Shipping aShipping() {
        var newFullName = new FullName("Jhon", "Doe");
        var newPhone = new Phone("54-454-1145");
        var newDocument = new Document("552-11-4335");
        var newAddress = anAddress();

        var recipient = Recipient.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .build();


        return Shipping.builder()
                .recipient(recipient)
                .address(newAddress)
                .cost(new Money("10.00"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .build();
    }

    public static Shipping anAltShipping() {
        var newFullName = new FullName("Mary", "Jones");
        var newPhone = new Phone("54-454-1144");
        var newDocument = new Document("552-11-4333");
        var newAddress = anAltAddress();

        var recipient = Recipient.builder()
                .fullName(newFullName)
                .phone(newPhone)
                .document(newDocument)
                .build();


        return Shipping.builder()
                .recipient(recipient)
                .address(newAddress)
                .cost(new Money("10.00"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .build();
    }


    public static Address anAddress() {
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

    public static Address anAltAddress() {
        return new Address(
                "Sansome Street",
                "875",
                "House",
                "Sansome",
                "San Francisco",
                "CA",
                new ZipCode("08040")
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

    public OrderTestDataBuilder shippingInfo(Shipping shipping) {
        this.shipping = shipping;
        return this;
    }

    public OrderTestDataBuilder billingInfo(Billing billing) {
        this.billing = billing;
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
