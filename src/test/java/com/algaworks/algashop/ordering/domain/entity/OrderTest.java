package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

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

        Set<OrderItem> items = order.items();

        Assertions.assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(items::clear);
    }

}