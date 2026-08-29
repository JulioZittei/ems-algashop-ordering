package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.*;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(String message) {
        super(message);
    }

    public OrderDoesNotContainOrderItemException(OrderId orderId, OrderItemId orderItemId) {
       super(String.format(ERROR_ORDER_DOES_NOT_CONTAIN_ORDER_ITEM,orderId, orderItemId));
    }

}
