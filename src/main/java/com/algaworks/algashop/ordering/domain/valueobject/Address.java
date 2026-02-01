package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.validator.FieldValidations;
import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public record Address(
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        ZipCode zipCode
) {

    @Builder(toBuilder = true)
    public Address(String street, String number, String complement, String neighborhood, String city, String state, ZipCode zipCode) {
        FieldValidations.requiresNonBlank(street, VALIDATION_ERROR_MESSAGE_BLANK_STREET);
        FieldValidations.requiresNonBlank(number, VALIDATION_ERROR_MESSAGE_BLANK_NUMBER);
        FieldValidations.requiresNonBlank(neighborhood, VALIDATION_ERROR_MESSAGE_BLANK_NEIGHBORHOOD);
        FieldValidations.requiresNonBlank(city, VALIDATION_ERROR_MESSAGE_BLANK_CITY);
        FieldValidations.requiresNonBlank(state, VALIDATION_ERROR_MESSAGE_BLANK_STATE);
        Objects.requireNonNull(zipCode, VALIDATION_ERROR_MESSAGE_BLANK_ZIPCODE);

        this.street = street;
        this.number = number;
        this.complement = complement;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
