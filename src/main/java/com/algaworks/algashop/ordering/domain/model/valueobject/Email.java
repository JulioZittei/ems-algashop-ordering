package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.validator.FieldValidations;

public record Email(String value) {

    public Email(String value) {
        FieldValidations.requiresValidEmail(value.trim());

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
