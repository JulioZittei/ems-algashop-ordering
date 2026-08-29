package com.algaworks.algashop.ordering.domain.model.commons;

import com.algaworks.algashop.ordering.domain.model.FieldValidations;

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
