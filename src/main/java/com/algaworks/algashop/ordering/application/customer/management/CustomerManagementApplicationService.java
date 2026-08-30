package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.ApplicationService;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.domain.model.commons.*;
import com.algaworks.algashop.ordering.domain.model.customer.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@ApplicationService
@RequiredArgsConstructor
public class CustomerManagementApplicationService {

    private final CustomerRegistrationService customerRegistrationService;
    private final Customers customers;

    @Transactional
    public UUID create(@NonNull CustomerInput customerInput) {

        Customer customer = customerRegistrationService.register(
                new FullName(customerInput.getFirstName(), customerInput.getLastName()),
                new BirthDate(customerInput.getBirthDate()),
                new Email(customerInput.getEmail()),
                new Phone(customerInput.getPhone()),
                new Document(customerInput.getDocument()),
                customerInput.getPromotionNotificationsAllowed(),
                Address.builder()
                        .street(customerInput.getAddressStreet())
                        .number(customerInput.getAddressNumber())
                        .complement(customerInput.getAddressComplement())
                        .neighborhood(customerInput.getAddressNeighborhood())
                        .city(customerInput.getAddressCity())
                        .state(customerInput.getAddressState())
                        .zipCode(new ZipCode(customerInput.getAddressZipCode()))
                        .build()
        );

        customers.add(customer);

        return customer.id().value();
    }

    @Transactional
    public CustomerOutput findById(@NonNull UUID customerUUID) {
        CustomerId customerId = new CustomerId(customerUUID);
        Customer customer = customers.ofId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        return CustomerOutput.builder()
                .id(customer.id().value())
                .firstName(customer.fullName().firstName())
                .lastName(customer.fullName().lastName())
                .email(customer.email().value())
                .phone(customer.phone().value())
                .document(customer.document().value())
                .birthDate(customer.birthDate() != null ? customer.birthDate().value() : null)
                .address(AddressData.builder()
                        .street(customer.addressStreet())
                        .number(customer.addressNumber())
                        .complement(customer.addressComplement())
                        .neighborhood(customer.addressNeighborhood())
                        .city(customer.addressCity())
                        .state(customer.addressState())
                        .zipCode(customer.addressZipCode().value())
                        .build())
                .promotionNotificationsAllowed(customer.isPromotionNotificationsAllowed())
                .arquived(customer.isArchived())
                .registeredAt(customer.registeredAt())
                .arquivedAt(customer.archivedAt() != null ? customer.archivedAt() : null)
                .build();
    }

}
