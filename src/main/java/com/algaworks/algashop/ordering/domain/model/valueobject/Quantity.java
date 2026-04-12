package com.algaworks.algashop.ordering.domain.model.valueobject;

import jakarta.annotation.Nonnull;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

public record Quantity(Integer value) implements Comparable<Quantity>{

    public static final Quantity ZERO = new Quantity(0);

    public Quantity(Integer value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);

        if (value < 0 )
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_NEGATIVE_QUANTITY);

        this.value = value;
    }

    public Quantity add(Quantity other) {
        Objects.requireNonNull(other);
        return new Quantity(this.value + other.value);
    }

    @Override
    public int compareTo(@Nonnull Quantity other) {
        Objects.requireNonNull(other);
        return this.value.compareTo(other.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
