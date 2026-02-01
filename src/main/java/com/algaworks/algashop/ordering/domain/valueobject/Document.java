package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.validator.FieldValidations;

public record Document(String value) {

    public Document(String value) {
        FieldValidations.requiresNonBlank(value);

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
