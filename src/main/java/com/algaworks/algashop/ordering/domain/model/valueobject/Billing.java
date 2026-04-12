package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages;
import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;

public record Billing(
        FullName fullName,
        Document document,
        Phone phone,
        Email email,
        Address address
) {

    @Builder(toBuilder = true)
    public Billing(FullName fullName, Document document, Phone phone, Email email, Address address) {
        Objects.requireNonNull(fullName, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_FULLNAME);
        Objects.requireNonNull(document, VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT);
        Objects.requireNonNull(phone, VALIDATION_ERROR_MESSAGE_NULL_PHONE);
        Objects.requireNonNull(email, VALIDATION_ERROR_MESSAGE_NULL_EMAIL);
        Objects.requireNonNull(address, VALIDATION_ERROR_MESSAGE_NULL_ADDRESS);

        this.fullName = fullName;
        this.document = document;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

}
