package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.domain.model.customer.CustomerNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Sql(statements = { "DELETE FROM order_item", "DELETE FROM \"order\"","DELETE FROM \"customer\"" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerManagementApplicationServiceIT {

    @Autowired
    private CustomerManagementApplicationService customerManagementApplicationService;

    @Test
    void shouldRegister() {
        CustomerInput input = CustomerInput.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1991, 7,5))
                .document("255-08-0578")
                .phone("478-256-2604")
                .email("johndoe@email.com")
                .promotionNotificationsAllowed(false)
                .address(AddressData.builder()
                        .street("Bourbon Street")
                        .number("1200")
                        .complement("Apt. 901")
                        .neighborhood("North Ville")
                        .city("Yostfort")
                        .state("South Carolina")
                        .zipCode("70283")
                        .build())
                .build();

        UUID customerId = customerManagementApplicationService.create(input);

        Assertions.assertThat(customerId).isNotNull();
    }

    @Test
    void shouldFindById() {
        CustomerInput input = CustomerInput.builder()
                .firstName("John")
                .lastName("Doe")
                .birthDate(LocalDate.of(1991, 7,5))
                .document("255-08-0578")
                .phone("478-256-2604")
                .email("johndoe@email.com")
                .promotionNotificationsAllowed(false)
                .address(AddressData.builder()
                        .street("Bourbon Street")
                        .number("1200")
                        .complement("Apt. 901")
                        .neighborhood("North Ville")
                        .city("Yostfort")
                        .state("South Carolina")
                        .zipCode("70283")
                        .build())
                .build();

        UUID customerId = customerManagementApplicationService.create(input);

        Assertions.assertThat(customerId).isNotNull();

        CustomerOutput customerOutput = customerManagementApplicationService.findById(customerId);

        Assertions.assertThat(customerOutput).isNotNull();
        Assertions.assertThat(customerOutput.getId()).isEqualTo(customerId);
        Assertions.assertThat(customerOutput.getFirstName()).isEqualTo(input.getFirstName());
        Assertions.assertThat(customerOutput.getLastName()).isEqualTo(input.getLastName());
        Assertions.assertThat(customerOutput.getEmail()).isEqualTo(input.getEmail());
        Assertions.assertThat(customerOutput.getPhone()).isEqualTo(input.getPhone());
        Assertions.assertThat(customerOutput.getDocument()).isEqualTo(input.getDocument());
        Assertions.assertThat(customerOutput.getBirthDate()).isEqualTo(input.getBirthDate());
        Assertions.assertThat(customerOutput.getPromotionNotificationsAllowed()).isEqualTo(input.getPromotionNotificationsAllowed());
        Assertions.assertThat(customerOutput.getAddress()).isNotNull();
        Assertions.assertThat(customerOutput.getAddress().getStreet()).isEqualTo(input.getAddressStreet());
        Assertions.assertThat(customerOutput.getAddress().getNumber()).isEqualTo(input.getAddressNumber());
        Assertions.assertThat(customerOutput.getAddress().getComplement()).isEqualTo(input.getAddressComplement());
        Assertions.assertThat(customerOutput.getAddress().getNeighborhood()).isEqualTo(input.getAddressNeighborhood());
        Assertions.assertThat(customerOutput.getAddress().getCity()).isEqualTo(input.getAddressCity());
        Assertions.assertThat(customerOutput.getAddress().getState()).isEqualTo(input.getAddressState());
        Assertions.assertThat(customerOutput.getAddress().getZipCode()).isEqualTo(input.getAddressZipCode());
    }

    @Test
    void shouldThrowNullPointerError_whenCustomerInputIsNull() {
       assertThrows(NullPointerException.class, () -> customerManagementApplicationService.create(null));
    }

    @Test
    void shouldThrowCustomerNotFoundError_whenCustomerIdDoesNotExists() {
        assertThrows(CustomerNotFoundException.class, () -> customerManagementApplicationService.findById(UUID.randomUUID()));
    }
}