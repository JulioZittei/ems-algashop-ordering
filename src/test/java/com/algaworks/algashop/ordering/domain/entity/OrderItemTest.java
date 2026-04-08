package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.Money;
import com.algaworks.algashop.ordering.domain.valueobject.ProductName;
import com.algaworks.algashop.ordering.domain.valueobject.Quantity;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.ProductId;
import org.junit.jupiter.api.Test;

class OrderItemTest {
    
    @Test
    void shouldGenerate() {
        OrderItem novoProduto = OrderItem.brandNew()
                .orderId(new OrderId())
                .productId( new ProductId())
                .productName(new ProductName("Novo produto"))
                .price( new Money("10.0"))
                .quantity(new Quantity(1))
                .build();
    }

}