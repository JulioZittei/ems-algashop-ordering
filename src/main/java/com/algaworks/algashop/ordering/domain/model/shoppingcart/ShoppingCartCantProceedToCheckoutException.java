package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.DomainException;

import static com.algaworks.algashop.ordering.domain.model.ErrorMessages.ERROR_SHOPPING_CART_CAN_NOT_PROCEED_TO_CHECKOUT;

public class ShoppingCartCantProceedToCheckoutException extends DomainException {

    public ShoppingCartCantProceedToCheckoutException(ShoppingCartId shoppingCartId) {
        super(String.format(ERROR_SHOPPING_CART_CAN_NOT_PROCEED_TO_CHECKOUT, shoppingCartId));
    }

    public ShoppingCartCantProceedToCheckoutException(String message, Throwable cause) {
        super(message, cause);
    }
}