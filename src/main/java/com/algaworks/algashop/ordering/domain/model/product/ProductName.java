package com.algaworks.algashop.ordering.domain.model.product;

import com.algaworks.algashop.ordering.domain.model.FieldValidations;

public record ProductName(String value) {

    public ProductName(String value) {
        FieldValidations.requiresNonBlank(value);

        this.value = value.trim();
    }

    @Override
    public String toString() {
        return value;
    }
}
