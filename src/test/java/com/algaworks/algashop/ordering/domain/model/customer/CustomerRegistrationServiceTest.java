package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.commons.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerRegistrationServiceTest {

    @Mock
    private Customers customers;

    @InjectMocks
    CustomerRegistrationService customerRegistrationService;

    @Test
    void shouldRegister() {
        when(customers.isEmailUnique(any(Email.class), any(CustomerId.class)))
                .thenReturn(true);

        Customer customer = customerRegistrationService.register(
                new FullName("John", "Doe"),
                new BirthDate(LocalDate.of(1991, 7, 5)),
                new Email("johndoe@email.com"),
                new Phone("478-256-2604"),
                new Document("255-08-0578"),
                true,
                Address.builder()
                        .street("Bourbon Street")
                        .number("1134")
                        .neighborhood("North Ville")
                        .city("Yostfort")
                        .state("South Carolina")
                        .zipCode(new ZipCode("70283"))
                        .complement("Apt. 901")
                        .build()
        );

        assertThat(customer.id()).isNotNull();

        assertThat(customer.fullName())
                .isEqualTo(new FullName("John", "Doe"));

        assertThat(customer.birthDate())
                .isEqualTo(new BirthDate(LocalDate.of(1991, 7, 5)));

        assertThat(customer.email())
                .isEqualTo(new Email("johndoe@email.com"));

        assertThat(customer.phone())
                .isEqualTo(new Phone("478-256-2604"));

        assertThat(customer.document())
                .isEqualTo(new Document("255-08-0578"));

        assertThat(customer.isPromotionNotificationsAllowed())
                .isTrue();

        assertThat(customer.address()).usingRecursiveComparison()
                .isEqualTo(
                        Address.builder()
                                .street("Bourbon Street")
                                .number("1134")
                                .neighborhood("North Ville")
                                .city("Yostfort")
                                .state("South Carolina")
                                .zipCode(new ZipCode("70283"))
                                .complement("Apt. 901")
                                .build()
                );

        assertThat(customer.registeredAt()).isNotNull();

        verify(customers).isEmailUnique(
                eq(new Email("johndoe@email.com")),
                any(CustomerId.class)
        );
    }

}