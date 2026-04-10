package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderItemTest {
    
    @Test
    void shouldGenerateBrandNewOrderItem() {
        var orderId = new OrderId();
        var product = ProductTestDataBuilder.aProduct().build();
        var quantity = new Quantity(1);

        OrderItem orderItem = OrderItem.brandNew()
                .orderId(orderId)
                .product(product)
                .quantity(quantity)
                .build();

        Assertions.assertThat(orderItem.id()).isNotNull();
        Assertions.assertThat(orderItem.productName()).isEqualTo(product.name());
        Assertions.assertThat(orderItem.price()).isEqualTo(product.price());
        Assertions.assertThat(orderItem.orderId()).isEqualTo(orderId);
        Assertions.assertThat(orderItem.productId()).isEqualTo(product.id());
        Assertions.assertThat(orderItem.quantity()).isEqualTo(quantity);
    }

}