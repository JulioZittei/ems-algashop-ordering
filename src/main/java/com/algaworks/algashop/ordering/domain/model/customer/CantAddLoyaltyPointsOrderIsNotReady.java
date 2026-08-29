package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.DomainException;
import com.algaworks.algashop.ordering.domain.model.order.OrderId;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_CAN_NOT_ADD_LOYALTY_POINTS_ORDER_IS_NOT_READY;

public class CantAddLoyaltyPointsOrderIsNotReady extends DomainException {


    public CantAddLoyaltyPointsOrderIsNotReady(OrderId orderId) {
        super(String.format(ERROR_CAN_NOT_ADD_LOYALTY_POINTS_ORDER_IS_NOT_READY, orderId));
    }

    public CantAddLoyaltyPointsOrderIsNotReady(String message, Throwable cause) {
        super(message, cause);
    }
}
