package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public record ShippingInfo(
        FullName fullName,
        Document document,
        Phone phone,
        Address address
) {

    @Builder(toBuilder = true)
    public ShippingInfo(FullName fullName, Document document, Phone phone, Address address) {
        Objects.requireNonNull(fullName, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_FULLNAME);
        Objects.requireNonNull(document, VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT);
        Objects.requireNonNull(phone, VALIDATION_ERROR_MESSAGE_NULL_PHONE);
        Objects.requireNonNull(address, VALIDATION_ERROR_MESSAGE_NULL_ADDRESS);

        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
        this.address = address;
    }
}
