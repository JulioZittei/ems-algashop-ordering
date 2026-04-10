package com.algaworks.algashop.ordering.domain.exception;

public class ErrorMessages {

    private ErrorMessages() {}

    public static final String VALIDATION_ERROR_MESSAGE_NULL_VALUE = "value cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_VALUE = "value cannot be blank.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_ADDRESS = "address cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_STREET = "street is required.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_NUMBER = "number is required.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_NEIGHBORHOOD = "neighborhood is required.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_CITY = "city is required.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_STATE = "city is required.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_ZIPCODE = "zipCode is required.";
    public static final String VALIDATION_ERROR_MESSAGE_CHARACTERS_VALUE = "value must be exactly {size} characters in length.";
    public static final String VALIDATION_ERROR_MESSAGE_INVALID_EMAIL = "email is not valid.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_EMAIL = "email cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_PHONE = "phone cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT = "document cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_FULLNAME = "fullName cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_FIRST_NAME = "firstName cannot be blank.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_FIRST_NAME = "firstName cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_LAST_NAME = "lastName cannot be blank.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_LAST_NAME = "lastName cannot be null.";
    public static final String VALIDATION_ERROR_MESSAGE_INVALID_BIRTHDATE = "birthDate cannot be a future date.";
    public static final String VALIDATION_ERROR_MESSAGE_NEGATIVE_LOYALTY_POINTS = "loyaltyPoints cannot be negative.";
    public static final String VALIDATION_ERROR_MESSAGE_NEGATIVE_QUANTITY = "quantity cannot be negative.";
    public static final String VALIDATION_ERROR_MESSAGE_ADD_ZERO_LOYALTY_POINTS = "loyaltyPoints cannot be increased by zero.";
    public static final String VALIDATION_ERROR_MESSAGE_NEGATIVE_MONEY = "money cannot be negative.";
    public static final String VALIDATION_ERROR_MESSAGE_BLANK_NULL_MONEY = "money cannot be null or blank.";
    public static final String VALIDATION_ERROR_MESSAGE_NULL_MONEY = "money cannot be null.";


    public static final String ERROR_MESSAGE_CUSTOMER_ARCHIVED = "customer is archived and it cannot be changed.";
    public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGE = "status in an order '%s' cannot change from '%s' to '%s'.";
    public static final String ERROR_ORDER_DELIVERY_CANNOT_BE_IN_THE_PAST = "expected delivery date in order '%s' cannot be in the past.";
    public static final String ERROR_ORDER_HAS_NO_ITEMS_CANNOT_BE_PLACED = "items in order '%s' cannot be empty for the order to be placed.";
    public static final String ERROR_ORDER_HAS_NO_SHIPPING_INFO_CANNOT_BE_PLACED = "shipping info in order '%s' cannot be null for the order to be placed.";
    public static final String ERROR_ORDER_HAS_NO_BILLING_INFO_CANNOT_BE_PLACED = "billing info in order '%s' cannot be null for the order to be placed.";
    public static final String ERROR_ORDER_HAS_NO_PAYMENT_METHOD_CANNOT_BE_PLACED = "expected delivery date in order '%s' cannot be null for the order to be placed.";
    public static final String ERROR_ORDER_DOES_NOT_CONTAIN_ORDER_ITEM = "order '%s' does not contain order item '%s'.";
    public static final String ERROR_PRODUCT_IS_OUT_OF_STOCK = "product '%s' is out of stock.";
    public static final String ERROR_ORDER_CANNOT_BE_EDITED = "order '%s' on status '%s' cannot be edited.";
    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_ITEM = "shopping cart '%s' does not contain item '%s'";
    public static final String ERROR_SHOPPING_CART_DOES_NOT_CONTAIN_PRODUCT = "shopping cart '%s' does not contain product %s";
    public static final String ERROR_SHOPPING_CART_ITEM_INCOMPATIBLE_PRODUCT = "shopping cart '%s' cannot be updated, incompatible product '%s'";

}
