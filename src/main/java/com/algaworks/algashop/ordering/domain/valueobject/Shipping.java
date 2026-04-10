package com.algaworks.algashop.ordering.domain.valueobject;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Objects;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_ADDRESS;

public record Shipping(
        Recipient recipient,
        Address address,
        Money cost,
        LocalDate expectedDate
) {

    @Builder(toBuilder = true)
    public Shipping(Recipient recipient, Address address, Money cost, LocalDate expectedDate) {
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(address, VALIDATION_ERROR_MESSAGE_NULL_ADDRESS);
        Objects.requireNonNull(cost);
        Objects.requireNonNull(expectedDate);

        this.recipient = recipient;
        this.address = address;
        this.cost = cost;
        this.expectedDate = expectedDate;
    }
}
