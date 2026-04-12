package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.validator.FieldValidations;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

public record Money(BigDecimal value) implements Comparable<Money> {

    private static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private static final int SCALE = 2;
    public static final Money ZERO = new Money(BigDecimal.ZERO);

    public Money(String value) {
        this(parseStringToBigDecimal(value));
    }

    public Money(BigDecimal value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_MONEY);

        if (value.signum() == -1)
            throw new IllegalArgumentException(VALIDATION_ERROR_MESSAGE_NEGATIVE_MONEY);

        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    private static BigDecimal parseStringToBigDecimal(String value) {
        FieldValidations.requiresNonBlank(value, VALIDATION_ERROR_MESSAGE_BLANK_NULL_MONEY);

        try {
            return new BigDecimal(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("value is not a number.", e);
        }
    }

    public Money multiply(Quantity quantity) {
        Objects.requireNonNull(quantity);
        if (quantity.value() < 1)
            throw new IllegalArgumentException("money cannot be multiplied by quantity less than 1");
        return new Money(this.value.multiply(BigDecimal.valueOf(quantity.value())));
    }

    public Money add(Money other) {
        Objects.requireNonNull(other);
        return new Money(this.value.add(other.value));
    }

    public Money divide(Money other) {
        Objects.requireNonNull(other);
        return new Money(this.value.divide(other.value, SCALE, ROUNDING_MODE));
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public int compareTo(@Nonnull Money other) {
        Objects.requireNonNull(other);
        return this.value().compareTo(other.value());
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass()) return false;

        Money that = (Money) other;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
