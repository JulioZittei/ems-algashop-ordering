package com.algaworks.algashop.ordering.domain.valueobject.id;

import com.algaworks.algashop.ordering.domain.utility.IdGenerator;
import com.algaworks.algashop.ordering.validator.FieldValidations;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;

public record OrderItemId(TSID value) {

    public OrderItemId() {
        this(IdGenerator.generateTSID());
    }

    public OrderItemId {
      Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);
    }

    public OrderItemId(Long value) {
        this(parseLongToTSID(value));
    }

    public OrderItemId(String value) {
        this(parseStringToTSID(value));
    }

    private static TSID parseLongToTSID(Long value) {
        Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);
        return TSID.from(value);
    }

    private static TSID parseStringToTSID(String value) {
        FieldValidations.requiresNonBlank(value);
        return TSID.from(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
