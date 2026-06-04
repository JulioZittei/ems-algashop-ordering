package com.algaworks.algashop.ordering.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = "id")
@Table(name = "order_item")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class OrderItemPersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id")
    private OrderPersistenceEntity order;

    private UUID productId;
    private String productName;

    private BigDecimal price;
    private Integer quantity;

    private BigDecimal totalAmount;

    public Long getOrderId() {
        if(Objects.isNull(getOrder())) {
            return null;
        }
        return order.getId();
    }
}
