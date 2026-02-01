package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.valueobject.Email;
import com.algaworks.algashop.ordering.domain.valueobject.FullName;
import com.algaworks.algashop.ordering.domain.valueobject.LoyaltyPoints;
import com.algaworks.algashop.ordering.domain.valueobject.Phone;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    @Test
    void given_invalidEmail_whenTryCreateCustomer_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> CustomerTestDataBuilder.brandNew()
                        .email(new Email("invalid"))
                        .build());
    }

    @Test
    void given_invalidEmail_whenTryUpdatedCustomerEmail_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> customer.changeEmail(new Email("invalid")));
    }

    @Test
    void given_unarchivedCustomer_whenArchive_shouldAnonymize() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

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
            c -> assertThat(c.isPromotionNotificationsAllowed()).isFalse(),
            c -> assertThat(c.address().street()).isEqualTo("Rua das Acacias"),
            c -> assertThat(c.address().number()).isEqualTo("Anonymized"),
            c -> assertThat(c.address().complement()).isNull(),
            c -> assertThat(c.address().neighborhood()).isEqualTo("Jardim Das Flores"),
            c -> assertThat(c.address().city()).isEqualTo("São Paulo"),
            c -> assertThat(c.address().state()).isEqualTo("São Paulo"),
            c -> assertThat(c.address().zipCode().value()).isEqualTo("12345")
        );
    }

    @Test
    void given_archivedCustomer_whenTryToUpdate_shouldAnonymizeGenerateCustomerArchivedException() {
        Customer customer = CustomerTestDataBuilder.existingArchived().build();

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
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        customer.addLoyaltyPoints(new LoyaltyPoints(10));
        customer.addLoyaltyPoints(new LoyaltyPoints(20));

       Assertions.assertThat(customer.loyaltyPoints()).isEqualTo(new LoyaltyPoints(30));
    }

    @Test
    void given_brainNewCustomer_whenAddInvalidLoyaltyPoints_shouldGenerateException() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(0)));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> customer.addLoyaltyPoints(new LoyaltyPoints(-20)));
    }
}