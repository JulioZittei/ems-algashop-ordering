package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity.OrderPersistenceEntityBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

public class OrderPersistenceEntityTestDataBuilder {

    private OrderPersistenceEntityTestDataBuilder() {}

    public static OrderPersistenceEntityBuilder existingOrder() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(2)
                .totalAmount(new BigDecimal("2000.00"))
                .paymentMethod("CREDIT_CARD")
                .status("PLACED")
                .placedAt(OffsetDateTime.now())
                .shipping(aShipping())
                .billing(aBilling())
                .items(Set.of(anOrderItem()));
    }

    public static OrderPersistenceEntityBuilder existingOrderWithAltData() {
        return OrderPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .customer(CustomerPersistenceEntityTestDataBuilder.aCustomer().build())
                .totalItems(3)
                .totalAmount(new BigDecimal("3500.00"))
                .paymentMethod("DEBIT_CARD")
                .status("PAID")
                .placedAt(OffsetDateTime.now().minusDays(2))
                .paidAt(OffsetDateTime.now().minusDays(1))
                .shipping(anAltShipping())
                .billing(anAltBilling());
    }

    private static ShippingEmbeddable aShipping() {
        return ShippingEmbeddable.builder()
                .cost(new BigDecimal("10.00"))
                .expectedDate(LocalDate.now().plusWeeks(1))
                .address(aShippingAddress())
                .recipient(aShippingRecipient())
                .build();
    }

    private static ShippingEmbeddable anAltShipping() {
        return ShippingEmbeddable.builder()
                .cost(new BigDecimal("15.50"))
                .expectedDate(LocalDate.now().plusWeeks(2))
                .address(anAltShippingAddress())
                .recipient(anAltShippingRecipient())
                .build();
    }

    private static AddressEmbeddable aShippingAddress() {
        return AddressEmbeddable.builder()
                .street("Rua das Acacias")
                .number("123")
                .complement("Apto 101")
                .neighborhood("Jardim das Flores")
                .city("São Paulo")
                .state("SP")
                .zipCode("12345")
                .build();
    }

    private static AddressEmbeddable anAltShippingAddress() {
        return AddressEmbeddable.builder()
                .street("Sansome Street")
                .number("875")
                .complement("House")
                .neighborhood("Sansome")
                .city("San Francisco")
                .state("CA")
                .zipCode("08040")
                .build();
    }

    private static RecipientEmbeddable aShippingRecipient() {
        return RecipientEmbeddable.builder()
                .firstName("Jhon")
                .lastName("Doe")
                .document("552-11-4335")
                .phone("54-454-1145")
                .build();
    }

    private static RecipientEmbeddable anAltShippingRecipient() {
        return RecipientEmbeddable.builder()
                .firstName("Mary")
                .lastName("Jones")
                .document("552-11-4333")
                .phone("54-454-1144")
                .build();
    }

    private static BillingEmbeddable aBilling() {
        return BillingEmbeddable.builder()
                .firstName("Jhon")
                .lastName("Doe")
                .document("552-11-4335")
                .email("jhondoe@email.com")
                .phone("54-454-1145")
                .address(aBillingAddress())
                .build();
    }

    private static BillingEmbeddable anAltBilling() {
        return BillingEmbeddable.builder()
                .firstName("Mary")
                .lastName("Jones")
                .document("552-11-4333")
                .email("maryjones@email.com")
                .phone("54-454-1144")
                .address(anAltBillingAddress())
                .build();
    }

    private static AddressEmbeddable aBillingAddress() {
        return AddressEmbeddable.builder()
                .street("Rua das Acacias")
                .number("123")
                .complement("Apto 101")
                .neighborhood("Jardim das Flores")
                .city("São Paulo")
                .state("SP")
                .zipCode("12345")
                .build();
    }

    private static AddressEmbeddable anAltBillingAddress() {
        return AddressEmbeddable.builder()
                .street("Sansome Street")
                .number("875")
                .complement("House")
                .neighborhood("Sansome")
                .city("San Francisco")
                .state("CA")
                .zipCode("08040")
                .build();
    }

    private static OrderItemPersistenceEntity anOrderItem() {
        return OrderItemPersistenceEntity.builder()
                .id(IdGenerator.generateTSID().toLong())
                .productId(IdGenerator.generateTimeBasedUUID())
                .productName("Notebook X11")
                .price(new BigDecimal("3000.00"))
                .totalAmount(new BigDecimal("3000.00"))
                .quantity(1)
                .build();
    }
}