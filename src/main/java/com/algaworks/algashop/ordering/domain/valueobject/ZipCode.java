package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.validator.FieldValidations;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_CHARACTERS_VALUE;

public record ZipCode(String value) {

    private static final int ZIPCODE_CHARACTERS_SIZE = 5;

    public ZipCode(String value) {
        FieldValidations.requiresNonBlank(value);

        if (value.trim().length() != ZIPCODE_CHARACTERS_SIZE)
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_CHARACTERS_VALUE
                    .replace("{size}", String.valueOf(ZIPCODE_CHARACTERS_SIZE)));

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
