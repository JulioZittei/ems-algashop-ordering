package com.algaworks.algashop.ordering.domain.factory;

import com.algaworks.algashop.ordering.domain.entity.Order;
import com.algaworks.algashop.ordering.domain.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.domain.entity.PaymentMethod;
import com.algaworks.algashop.ordering.domain.entity.ProductTestDataBuilder;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.CustomerId;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderFactoryTest {


    @Test
    void shouldGenerateFilledOrderThatCanBePlaced() {
        var shipping = OrderTestDataBuilder.aShipping();
        var billing = OrderTestDataBuilder.aBilling();
        var paymentMethod = PaymentMethod.CREDIT_CARD;
        var product = ProductTestDataBuilder.aProduct().build();
        var quantity = new Quantity(2);

        Order order = OrderFactory.filled(
                new CustomerId(),
                shipping,
                billing,
                paymentMethod,
                product,
                quantity
        );

        Assertions.assertThat(order.isDraft()).isTrue();
        Assertions.assertThat(order.shipping()).isEqualTo(shipping);
        Assertions.assertThat(order.billing()).isEqualTo(billing);
        Assertions.assertThat(order.paymentMethod()).isEqualTo(paymentMethod);
        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(order.items().iterator().next().productName()).isEqualTo(product.name());
        Assertions.assertThat(order.items().iterator().next().price()).isEqualTo(product.price());
        Assertions.assertThat(order.totalItems()).isEqualTo(quantity);

        order.markAsPlaced();

        Assertions.assertThat(order.isPlaced()).isTrue();

    }
}