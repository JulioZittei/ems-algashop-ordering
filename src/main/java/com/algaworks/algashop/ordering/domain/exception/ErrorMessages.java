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
    public static final String VALIDATION_ERROR_MESSAGE_NEGATIVE_LOYALTY_POINTS = "loyaltyPoints cannot be positive.";
    public static final String VALIDATION_ERROR_MESSAGE_ADD_ZERO_LOYALTY_POINTS = "loyaltyPoints cannot be increased by zero.";


    public static final String ERROR_MESSAGE_CUSTOMER_ARCHIVED = "customer is archived and it cannot be changed.";
}
