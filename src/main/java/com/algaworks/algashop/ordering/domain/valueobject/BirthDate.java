package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;

public record BirthDate(LocalDate value) {

    public BirthDate(LocalDate value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);

        if(value.isAfter(LocalDate.now()))
            throw new IllegalArgumentException(ErrorMessages.VALIDATION_ERROR_MESSAGE_INVALID_BIRTHDATE);

        this.value = value;
    }

    public Integer age() {
        return Math.toIntExact(ChronoUnit.YEARS.between(this.value, LocalDate.now()));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
