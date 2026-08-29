package com.algaworks.algashop.ordering.domain.model.commons;

import com.algaworks.algashop.ordering.domain.model.FieldValidations;

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
