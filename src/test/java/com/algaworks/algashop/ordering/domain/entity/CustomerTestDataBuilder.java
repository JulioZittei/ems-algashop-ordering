package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.valueobject.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

public class CustomerTestDataBuilder {

    private CustomerTestDataBuilder() {}

    public static Customer.BrandNewCustomerBuilder brandNew() {
        return Customer.brandNew()
                .id(new CustomerId())
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .registeredAt(OffsetDateTime.now())
                .address(Address.builder()
                        .street("Rua das Acacias")
                        .number("250")
                        .complement("Casa 02")
                        .neighborhood("Jardim Das Flores")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode(new ZipCode("12345"))
                        .build());
    }

    public static Customer.ExistingCustomerBuilder existingArchived() {
        return Customer.existing()
                .id(new CustomerId())
                .fullName(new FullName("John", "Doe"))
                .birthDate(new BirthDate(LocalDate.of(1991, 7, 5)))
                .email(new Email("john.doe@gmail.com"))
                .phone(new Phone("478-256-2504"))
                .document(new Document("255-08-0578"))
                .promotionNotificationsAllowed(false)
                .archived(true)
                .registeredAt(OffsetDateTime.now())
                .archivedAt(OffsetDateTime.now())
                .loyaltyPoints(LoyaltyPoints.ZERO)
                .address(Address.builder()
                        .street("Rua das Acacias")
                        .number("250")
                        .complement("Casa 02")
                        .neighborhood("Jardim Das Flores")
                        .city("São Paulo")
                        .state("São Paulo")
                        .zipCode(new ZipCode("12345"))
                        .build());
    }
}
