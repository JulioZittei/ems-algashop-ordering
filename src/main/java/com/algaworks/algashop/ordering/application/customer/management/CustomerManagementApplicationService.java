package com.algaworks.algashop.ordering.application.customer.management;

import com.algaworks.algashop.ordering.application.ApplicationService;
import com.algaworks.algashop.ordering.application.commons.AddressData;
import com.algaworks.algashop.ordering.application.utility.Mapper;
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
    private final Mapper mapper;

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
    void update(@NonNull UUID customerUUID, @NonNull CustomerUpdateInput customerUpdateInput) {
        CustomerId customerId = new CustomerId(customerUUID);
        Customer customer = customers.ofId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        customer.changeFullName(new FullName(customerUpdateInput.getFirstName(), customerUpdateInput.getLastName()));
        customer.changePhone(new Phone(customerUpdateInput.getPhone()));

        if (Boolean.TRUE.equals(customerUpdateInput.getPromotionNotificationsAllowed())) {
            customer.enablePromotionNotifications();
        } else {
            customer.disablePromotionNotifications();
        }

        customer.changeAddress(Address.builder()
                .street(customerUpdateInput.getAddressStreet())
                .number(customerUpdateInput.getAddressNumber())
                .complement(customerUpdateInput.getAddressComplement())
                .neighborhood(customerUpdateInput.getAddressNeighborhood())
                .city(customerUpdateInput.getAddressCity())
                .state(customerUpdateInput.getAddressState())
                .zipCode(new ZipCode(customerUpdateInput.getAddressZipCode()))
                .build());

        customerRegistrationService.changeEmail(customer, new Email(customerUpdateInput.getEmail()));

        customers.add(customer);
    }

    @Transactional(readOnly = true)
    public CustomerOutput findById(@NonNull UUID customerUUID) {
        CustomerId customerId = new CustomerId(customerUUID);
        Customer customer = customers.ofId(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
        return mapper.convert(customer, CustomerOutput.class);
    }

}
