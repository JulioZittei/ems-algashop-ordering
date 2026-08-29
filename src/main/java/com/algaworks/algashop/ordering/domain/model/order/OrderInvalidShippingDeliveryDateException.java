package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_ORDER_DELIVERY_CANNOT_BE_IN_THE_PAST;

public class OrderInvalidShippingDeliveryDateException extends DomainException {
    public OrderInvalidShippingDeliveryDateException(String message) {
        super(message);
    }

    public OrderInvalidShippingDeliveryDateException(OrderId orderId) {
        super(String.format(ERROR_ORDER_DELIVERY_CANNOT_BE_IN_THE_PAST, orderId));
    }
}
