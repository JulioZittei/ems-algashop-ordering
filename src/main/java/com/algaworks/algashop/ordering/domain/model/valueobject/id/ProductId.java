package com.algaworks.algashop.ordering.domain.model.valueobject.id;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;

import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;

public record ProductId(UUID value) {

    public ProductId() {
        this(IdGenerator.generateTimeBasedUUID());
    }

    public ProductId(UUID value) {
        this.value = Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
