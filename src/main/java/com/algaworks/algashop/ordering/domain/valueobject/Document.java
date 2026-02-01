package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;

import java.util.Objects;

public record Document(String value) {

    public Document(String value) {
        Objects.requireNonNull(value, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE);

        if(value.trim().isBlank())
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_MESSAGE_BLANK_VALUE);

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
