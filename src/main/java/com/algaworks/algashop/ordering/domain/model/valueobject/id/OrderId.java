package com.algaworks.algashop.ordering.domain.model.valueobject.id;

import com.algaworks.algashop.ordering.domain.model.utility.IdGenerator;
import com.algaworks.algashop.ordering.domain.model.validator.FieldValidations;
import io.hypersistence.tsid.TSID;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;

public record OrderId(TSID value) {

    public OrderId() {
        this(IdGenerator.generateTSID());
    }

    public OrderId {
      Objects.requireNonNull(value, VALIDATION_ERROR_MESSAGE_NULL_VALUE);
    }

    public OrderId(Long value) {
        this(parseLongToTSID(value));
    }

    public OrderId(String value) {
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
