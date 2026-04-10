package com.algaworks.algashop.ordering.domain.exception;

import com.algaworks.algashop.ordering.domain.valueobject.id.OrderId;
import com.algaworks.algashop.ordering.domain.valueobject.id.OrderItemId;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public class OrderDoesNotContainOrderItemException extends DomainException {

    public OrderDoesNotContainOrderItemException(String message) {
        super(message);
    }

    public OrderDoesNotContainOrderItemException(OrderId orderId, OrderItemId orderItemId) {
       super(String.format(ERROR_ORDER_DOES_NOT_CONTAIN_ORDER_ITEM,orderId, orderItemId));
    }

}
