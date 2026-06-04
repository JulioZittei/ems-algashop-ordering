package com.algaworks.algashop.ordering.domain.model.exception;

import com.algaworks.algashop.ordering.domain.model.valueobject.id.OrderId;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.ERROR_CAN_NOT_ADD_LOYALTY_POINTS_ORDER_IS_NOT_READY;

public class CantAddLoyaltyPointsOrderIsNotReady extends DomainException {


    public CantAddLoyaltyPointsOrderIsNotReady(OrderId orderId) {
        super(String.format(ERROR_CAN_NOT_ADD_LOYALTY_POINTS_ORDER_IS_NOT_READY, orderId));
    }

    public CantAddLoyaltyPointsOrderIsNotReady(String message, Throwable cause) {
        super(message, cause);
    }
}
