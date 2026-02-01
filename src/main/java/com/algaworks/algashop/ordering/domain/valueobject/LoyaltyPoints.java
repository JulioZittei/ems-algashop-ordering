package com.algaworks.algashop.ordering.domain.valueobject;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public record LoyaltyPoints(Integer value) implements Comparable<LoyaltyPoints> {

    public static final LoyaltyPoints ZERO = new LoyaltyPoints(0);

    public LoyaltyPoints() {
       this(0);
    }

    public LoyaltyPoints(Integer value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);

        if (value < 0 )
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_NEGATIVE_LOYALTY_POINTS);

        this.value = value;
    }

    public LoyaltyPoints addLoyaltyPoints(Integer value) {
        return addLoyaltyPoints(new LoyaltyPoints(value));
    }

    public LoyaltyPoints addLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);

        if (loyaltyPoints.value() == 0 )
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_ADD_ZERO_LOYALTY_POINTS);

        return new LoyaltyPoints(this.value() + loyaltyPoints.value());
    }

    @Override
    public String toString() {
        return this.value().toString();
    }

    @Override
    public int compareTo(LoyaltyPoints o) {
        return this.value().compareTo(o.value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyPoints that = (LoyaltyPoints) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
