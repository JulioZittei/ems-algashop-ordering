package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

public class OrderCannotBePlacedException extends DomainException {

    private OrderCannotBePlacedException(String message) {
        super(message);
    }

    public static OrderCannotBePlacedException hasNoItems(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_HAS_NO_ITEMS_CANNOT_BE_PLACED,orderId));
    }

    public static OrderCannotBePlacedException hasNoShippingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_HAS_NO_SHIPPING_INFO_CANNOT_BE_PLACED,orderId));
    }

    public static OrderCannotBePlacedException hasNoBillingInfo(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_HAS_NO_BILLING_INFO_CANNOT_BE_PLACED,orderId));
    }

    public static OrderCannotBePlacedException hasNoPaymentMethod(OrderId orderId) {
        return new OrderCannotBePlacedException(String.format(ERROR_ORDER_HAS_NO_PAYMENT_METHOD_CANNOT_BE_PLACED,orderId));
    }
}
