package com.algaworks.algashop.ordering.validator;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_BLANK_VALUE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;

public class FieldValidations {

    private FieldValidations(){}

    public static void requiresNonBlank(final String value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);
        if (value.trim().isBlank())
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_BLANK_VALUE);
    }

    public static void requiresNonBlank(final String value, final String errorMessage) {
        Objects.requireNonNull(value, errorMessage);
        if (value.trim().isBlank())
            throw new IllegalArgumentException(errorMessage);
    }

    public static void requiresValidEmail(final String email) {
        requiresValidEmail(email, ErrorMessages.VALIDATION_ERROR_MESSAGE_INVALID_EMAIL);
    }

    public static void requiresValidEmail(final String email, final String errorMessage) {
        requiresNonBlank(email, errorMessage);
        if (!EmailValidator.getInstance().isValid(email)) throw new IllegalArgumentException(errorMessage);
    }
}
