package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Customer(
                        new CustomerId(),
                        new FullName("John",  "Doe"),
                            new BirthDate(LocalDate.of(1991, 7, 5)),
                            new Email("invalid"),
                            new Phone("478-256-2504"),
                            new Document("255-08-0578"),
                            false,
                            OffsetDateTime.now()
                    ));
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John",  "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> {
                    customer.changeEmail(new Email("invalid"));
                });
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John",  "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                true,
                OffsetDateTime.now()
        );

        customer.archive();

        Assertions.assertWith(customer,
                c -> assertThat(c.fullName()).isEqualTo(new FullName("Anonymous","Anonymous")),
                c -> assertThat(c.fullName().firstName()).isEqualTo("Anonymous"),
                c -> assertThat(c.fullName().lastName()).isEqualTo("Anonymous"),
                c -> assertThat(c.fullName().toString()).hasToString("Anonymous Anonymous"),
            c -> assertThat(c.email().value()).isNotEqualTo("john.doe@gmail.com"),
            c -> assertThat(c.phone().value()).isEqualTo("000-000-0000"),
            c -> assertThat(c.document().value()).isEqualTo("000-00-0000"),
            c -> assertThat(c.birthDate()).isNull(),
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse()
        );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldAnonymizeGenerateCustomerArchivedException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John",  "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                true,
                OffsetDateTime.now(),
                OffsetDateTime.now(),
                new LoyaltyPoints()
        );

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::archive);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeFullName(new FullName("Another Name", "Last Name")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changeEmail(new Email("another@email.com")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.changePhone(new Phone("478-256-2505")));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(10)));

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::enablePromotionNotifications);

        Assertions.assertThatExceptionOfType(CustomerArchivedException.class)
                .isThrownBy(customer::disablePromotionNotifications);
    }

    @Test
    void given_brainNewCustomer_whenAddLoyaltyPoints_shouldSomePoints() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John",  "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now()
        );

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

       Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brainNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = new Customer(
                new CustomerId(),
                new FullName("John",  "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("john.doe@gmail.com"),
                new Phone("478-256-2504"),
                new Document("255-08-0578"),
                false,
                OffsetDateTime.now()
        );

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(-20)));
    }
}