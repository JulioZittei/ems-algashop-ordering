package com.algaworks.algashop.ordering.domain.model.commons;

import com.algaworks.algashop.ordering.domain.model.FieldValidations;

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
