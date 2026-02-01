package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_BLANK_VALUE;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;

public record Phone(String value) {

    public Phone(String value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);

        if(value.trim().isBlank())
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_BLANK_VALUE);

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
