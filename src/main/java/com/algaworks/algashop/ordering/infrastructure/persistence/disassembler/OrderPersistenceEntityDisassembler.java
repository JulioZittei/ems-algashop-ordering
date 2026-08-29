package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.order.*;
import com.algaworks.algashop.ordering.domain.model.product.ProductName;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerId;
import com.algaworks.algashop.ordering.domain.model.product.ProductId;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.BillingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.RecipientEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.embeddable.ShippingEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceEntityDisassembler {

    public Order toDomainEntity(OrderPersistenceEntity orderPersistenceEntity) {
        return Order.existing()
                .id(new OrderId(orderPersistenceEntity.getId()))
                .customerId(new CustomerId(orderPersistenceEntity.getCustomerId()))
                .totalAmount(new Money(orderPersistenceEntity.getTotalAmount()))
                .totalItems(new Quantity(orderPersistenceEntity.getTotalItems()))
                .status(OrderStatus.valueOf(orderPersistenceEntity.getStatus()))
                .paymentMethod(PaymentMethod.valueOf(orderPersistenceEntity.getPaymentMethod()))
                .placedAt(orderPersistenceEntity.getPlacedAt())
                .paidAt(orderPersistenceEntity.getPaidAt())
                .readyAt(orderPersistenceEntity.getReadyAt())
                .canceledAt(orderPersistenceEntity.getCanceledAt())
                .items(orderPersistenceEntity.getItems().stream().map(this::toDomainEntity).collect(Collectors.toSet()))
                .billing(toBillingValueObject(orderPersistenceEntity.getBilling()))
                .shipping(toShippingValueObject(orderPersistenceEntity.getShipping()))
                .version(orderPersistenceEntity.getVersion())
                .build();
    }

    public OrderItem toDomainEntity(OrderItemPersistenceEntity orderItemPersistenceEntity) {
        return OrderItem.existing()
                .id(new OrderItemId(orderItemPersistenceEntity.getId()))
                .orderId(new OrderId(orderItemPersistenceEntity.getOrderId()))
                .productId(new ProductId(orderItemPersistenceEntity.getProductId()))
                .productName(new ProductName(orderItemPersistenceEntity.getProductName()))
                .price(new Money(orderItemPersistenceEntity.getPrice()))
                .quantity(new Quantity(orderItemPersistenceEntity.getQuantity()))
                .totalAmount(new Money(orderItemPersistenceEntity.getTotalAmount()))
                .build();
    }

    private Shipping toShippingValueObject(ShippingEmbeddable shippingEmbeddable) {
        if (Objects.isNull(shippingEmbeddable)) {
            return null;
        }
        RecipientEmbeddable recipientEmbeddable = shippingEmbeddable.getRecipient();
        return Shipping.builder()
                .cost(new Money(shippingEmbeddable.getCost()))
                .expectedDate(shippingEmbeddable.getExpectedDate())
                .recipient(
                        Recipient.builder()
                                .fullName(new FullName(recipientEmbeddable.getFirstName(), recipientEmbeddable.getLastName()))
                                .document(new Document(recipientEmbeddable.getDocument()))
                                .phone(new Phone(recipientEmbeddable.getPhone()))
                                .build()
                )
                .address(toAddressValueObject(shippingEmbeddable.getAddress()))
                .build();
    }

    private Billing toBillingValueObject(BillingEmbeddable billingEmbeddable) {
        if (Objects.isNull(billingEmbeddable)) {
            return null;
        }
        return Billing.builder()
                .fullName(new FullName(billingEmbeddable.getFirstName(), billingEmbeddable.getLastName()))
                .document(new Document(billingEmbeddable.getDocument()))
                .phone(new Phone(billingEmbeddable.getPhone()))
                .address(toAddressValueObject(billingEmbeddable.getAddress()))
                .email(new Email(billingEmbeddable.getEmail()))
                .build();
    }

    private Address toAddressValueObject(AddressEmbeddable addressEmbeddable) {
        if (Objects.isNull(addressEmbeddable)) {
            return null;
        }
        return Address.builder()
                .street(addressEmbeddable.getStreet())
                .number(addressEmbeddable.getNumber())
                .complement(addressEmbeddable.getComplement())
                .neighborhood(addressEmbeddable.getNeighborhood())
                .city(addressEmbeddable.getCity())
                .state(addressEmbeddable.getState())
                .zipCode(new ZipCode(addressEmbeddable.getZipCode()))
                .build();
    }
}
