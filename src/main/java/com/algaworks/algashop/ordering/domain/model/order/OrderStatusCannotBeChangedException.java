package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_ORDER_STATUS_CANNOT_BE_CHANGE;

public class OrderStatusCannotBeChangedException extends DomainException {
    public OrderStatusCannotBeChangedException(String message) {
        super(message);
    }

    public OrderStatusCannotBeChangedException(OrderId orderId, OrderStatus from, OrderStatus to) {
        super(String.format(ERROR_ORDER_STATUS_CANNOT_BE_CHANGE, orderId, from, to));
    }
}
