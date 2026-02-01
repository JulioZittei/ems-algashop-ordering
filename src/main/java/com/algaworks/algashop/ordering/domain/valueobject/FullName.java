package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;

import java.util.Objects;

public record FullName(String firstName, String lastName) {


    public FullName(String firstName, String lastName) {
        Objects.requireNonNull(firstName, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_FIRST_NAME);
        Objects.requireNonNull(lastName, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_LAST_NAME);

        if(firstName.trim().isBlank())
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_MESSAGE_BLANK_FIRST_NAME);

        if(lastName.trim().isBlank())
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_MESSAGE_BLANK_LAST_NAME);

        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
    }

    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }
}
