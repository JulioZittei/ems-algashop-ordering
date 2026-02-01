package com.algaworks.algashop.ordering.validator;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Objects;

public class FieldValidations {

    private FieldValidations(){}

    public static void requiresValidEmail(final String email) {
        requiresValidEmail(email, ErrorMessages.VALIDATION_ERROR_MESSAGE_INVALID_EMAIL);
    }

    public static void requiresValidEmail(final String email, final String errorMessage) {
        Objects.requireNonNull(email, errorMessage);
        if(email.trim().isBlank()) throw new IllegalArgumentException(errorMessage);
        if (!EmailValidator.getInstance().isValid(email)) throw new IllegalArgumentException(errorMessage);
    }
}
