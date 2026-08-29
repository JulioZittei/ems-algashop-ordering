package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.order.Order;
import com.algaworks.algashop.ordering.domain.model.order.OrderItem;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderItemPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.OrderPersistenceEntityTestDataBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderPersistenceEntityDisassemblerTest {

    private final OrderPersistenceEntityDisassembler disassembler = new OrderPersistenceEntityDisassembler();

    @Test
    void shouldConvertToDomainEntity() {
        OrderPersistenceEntity orderPersistence = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        Order order = disassembler.toDomainEntity(orderPersistence);

        assertThat(order.id().value().toLong()).isEqualTo(orderPersistence.getId());
        assertThat(order.customerId().value()).isEqualTo(orderPersistence.getCustomerId());
        assertThat(order.totalAmount().value()).isEqualTo(orderPersistence.getTotalAmount());
        assertThat(order.totalItems().value()).isEqualTo(orderPersistence.getTotalItems());
        assertThat(order.shippingCost().value()).isEqualTo(orderPersistence.getShippingCost());
        assertThat(order.shippingExpectedDate()).isEqualTo(orderPersistence.getShippingExpectedDate());
        assertThat(order.shippingRecipientFullName().firstName()).isEqualTo(orderPersistence.getShippingRecipientFirstName());
        assertThat(order.shippingRecipientFullName().lastName()).isEqualTo(orderPersistence.getShippingRecipientLastName());
        assertThat(order.shippingRecipientDocument().value()).isEqualTo(orderPersistence.getShippingRecipientDocument());
        assertThat(order.shippingRecipientPhone().value()).isEqualTo(orderPersistence.getShippingRecipientPhone());
        assertThat(order.shippingAddressStreet()).isEqualTo(orderPersistence.getShippingAddressStreet());
        assertThat(order.shippingAddressNumber()).isEqualTo(orderPersistence.getShippingAddressNumber());
        assertThat(order.shippingAddressComplement()).isEqualTo(orderPersistence.getShippingAddressComplement());
        assertThat(order.shippingAddressNeighborhood()).isEqualTo(orderPersistence.getShippingAddressNeighborhood());
        assertThat(order.shippingAddressCity()).isEqualTo(orderPersistence.getShippingAddressCity());
        assertThat(order.shippingAddressState()).isEqualTo(orderPersistence.getShippingAddressState());
        assertThat(order.shippingAddressZipCode().value()).isEqualTo(orderPersistence.getShippingAddressZipCode());
        assertThat(order.billingFullName().firstName()).isEqualTo(orderPersistence.getBillingFirstName());
        assertThat(order.billingFullName().lastName()).isEqualTo(orderPersistence.getBillingLastName());
        assertThat(order.billingDocument().value()).isEqualTo(orderPersistence.getBillingDocument());
        assertThat(order.billingPhone().value()).isEqualTo(orderPersistence.getBillingPhone());
        assertThat(order.billingEmail().value()).isEqualTo(orderPersistence.getBillingEmail());
        assertThat(order.billingAddressStreet()).isEqualTo(orderPersistence.getBillingAddressStreet());
        assertThat(order.billingAddressNumber()).isEqualTo(orderPersistence.getBillingAddressNumber());
        assertThat(order.billingAddressComplement()).isEqualTo(orderPersistence.getBillingAddressComplement());
        assertThat(order.billingAddressNeighborhood()).isEqualTo(orderPersistence.getBillingAddressNeighborhood());
        assertThat(order.billingAddressCity()).isEqualTo(orderPersistence.getBillingAddressCity());
        assertThat(order.billingAddressState()).isEqualTo(orderPersistence.getBillingAddressState());
        assertThat(order.billingAddressZipCode().value()).isEqualTo(orderPersistence.getBillingAddressZipCode());
        assertThat(order.status().name()).isEqualTo(orderPersistence.getStatus());
        assertThat(order.paymentMethod().name()).isEqualTo(orderPersistence.getPaymentMethod());
        assertThat(order.placedAt()).isEqualTo(orderPersistence.getPlacedAt());
        assertThat(order.paidAt()).isEqualTo(orderPersistence.getPaidAt());
        assertThat(order.readyAt()).isEqualTo(orderPersistence.getReadyAt());
        assertThat(order.canceledAt()).isEqualTo(orderPersistence.getCanceledAt());
        assertThat(order.version()).isEqualTo(orderPersistence.getVersion());
        Assertions.assertThat(order.items()).hasSize(1);


        OrderItem orderItem = order.items().iterator().next();
        OrderItemPersistenceEntity orderItemPersistence = orderPersistence.getItems().iterator().next();
        assertThat(orderItem.id().value().toLong()).isEqualTo(orderItemPersistence.getId());
        assertThat(orderItem.orderId().value().toLong()).isEqualTo(orderItemPersistence.getOrderId());
        assertThat(orderItem.productId().value()).isEqualTo(orderItemPersistence.getProductId());
        assertThat(orderItem.productName().value()).isEqualTo(orderItemPersistence.getProductName());
        assertThat(orderItem.price().value()).isEqualTo(orderItemPersistence.getPrice());
        assertThat(orderItem.quantity().value()).isEqualTo(orderItemPersistence.getQuantity());
        assertThat(orderItem.totalAmount().value()).isEqualTo(orderItemPersistence.getTotalAmount());
    }

}