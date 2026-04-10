package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import lombok.Builder;

import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT;
import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_PHONE;

@Builder
public record Recipient(
        FullName fullName,
        Document document,
        Phone phone
) {
    public Recipient {
        Objects.requireNonNull(fullName, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_FULLNAME);
        Objects.requireNonNull(document, VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT);
        Objects.requireNonNull(phone, VALIDATION_ERROR_MESSAGE_NULL_PHONE);
    }
}
