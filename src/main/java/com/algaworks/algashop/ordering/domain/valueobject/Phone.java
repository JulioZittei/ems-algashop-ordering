package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.validator.FieldValidations;

public record Phone(String value) {

    public Phone(String value) {
        FieldValidations.requiresNonBlank(value);

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
