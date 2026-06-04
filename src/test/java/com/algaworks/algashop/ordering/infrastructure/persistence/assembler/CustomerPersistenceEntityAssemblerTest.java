package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Customer;
import com.algaworks.algashop.ordering.domain.model.entity.CustomerTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerPersistenceEntityAssemblerTest {

    private final CustomerPersistenceEntityAssembler  assembler = new CustomerPersistenceEntityAssembler();

    @Test
    void shouldConvertFromDomain() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();
        CustomerPersistenceEntity customerPersistenceEntity = assembler.fromDomain(customer);

        assertThat(customerPersistenceEntity.getId()).isEqualTo(customer.id().value());
        assertThat(customerPersistenceEntity.getFirstName()).isEqualTo(customer.fullName().firstName());
        assertThat(customerPersistenceEntity.getLastName()).isEqualTo(customer.fullName().lastName());
        assertThat(customerPersistenceEntity.getBirthDate()).isEqualTo(customer.birthDate().value());
        assertThat(customerPersistenceEntity.getDocument()).isEqualTo(customer.document().value());
        assertThat(customerPersistenceEntity.getPhone()).isEqualTo(customer.phone().value());
        assertThat(customerPersistenceEntity.getEmail()).isEqualTo(customer.email().value());
        assertThat(customerPersistenceEntity.getArchived()).isEqualTo(customer.isArchived());
        assertThat(customerPersistenceEntity.getPromotionNotificationsAllowed()).isEqualTo(customer.isPromotionNotificationsAllowed());
        assertThat(customerPersistenceEntity.getRegisteredAt()).isEqualTo(customer.registeredAt());
        assertThat(customerPersistenceEntity.getArchivedAt()).isEqualTo(customer.archivedAt());
        assertThat(customerPersistenceEntity.getLoyaltyPoints()).isEqualTo(customer.loyaltyPoints().value());
        assertThat(customerPersistenceEntity.getVersion()).isEqualTo(customer.version());
        assertThat(customerPersistenceEntity.getAddressStreet()).isEqualTo(customer.addressStreet());
        assertThat(customerPersistenceEntity.getAddressNumber()).isEqualTo(customer.addressNumber());
        assertThat(customerPersistenceEntity.getAddressComplement()).isEqualTo(customer.addressComplement());
        assertThat(customerPersistenceEntity.getAddressNeighborhood()).isEqualTo(customer.addressNeighborhood());
        assertThat(customerPersistenceEntity.getAddressCity()).isEqualTo(customer.addressCity());
        assertThat(customerPersistenceEntity.getAddressState()).isEqualTo(customer.addressState());
        assertThat(customerPersistenceEntity.getAddressZipCode()).isEqualTo(customer.addressZipCode().value());
    }

    @Test
    void shouldMerge() {
        Customer customer = CustomerTestDataBuilder.brandNew().build();
        CustomerPersistenceEntity customerPersistenceEntity = assembler.merge(new CustomerPersistenceEntity(), customer);

        assertThat(customerPersistenceEntity.getId()).isEqualTo(customer.id().value());
        assertThat(customerPersistenceEntity.getFirstName()).isEqualTo(customer.fullName().firstName());
        assertThat(customerPersistenceEntity.getLastName()).isEqualTo(customer.fullName().lastName());
        assertThat(customerPersistenceEntity.getBirthDate()).isEqualTo(customer.birthDate().value());
        assertThat(customerPersistenceEntity.getDocument()).isEqualTo(customer.document().value());
        assertThat(customerPersistenceEntity.getPhone()).isEqualTo(customer.phone().value());
        assertThat(customerPersistenceEntity.getEmail()).isEqualTo(customer.email().value());
        assertThat(customerPersistenceEntity.getArchived()).isEqualTo(customer.isArchived());
        assertThat(customerPersistenceEntity.getPromotionNotificationsAllowed()).isEqualTo(customer.isPromotionNotificationsAllowed());
        assertThat(customerPersistenceEntity.getRegisteredAt()).isEqualTo(customer.registeredAt());
        assertThat(customerPersistenceEntity.getArchivedAt()).isEqualTo(customer.archivedAt());
        assertThat(customerPersistenceEntity.getLoyaltyPoints()).isEqualTo(customer.loyaltyPoints().value());
        assertThat(customerPersistenceEntity.getVersion()).isEqualTo(customer.version());
        assertThat(customerPersistenceEntity.getAddressStreet()).isEqualTo(customer.addressStreet());
        assertThat(customerPersistenceEntity.getAddressNumber()).isEqualTo(customer.addressNumber());
        assertThat(customerPersistenceEntity.getAddressComplement()).isEqualTo(customer.addressComplement());
        assertThat(customerPersistenceEntity.getAddressNeighborhood()).isEqualTo(customer.addressNeighborhood());
        assertThat(customerPersistenceEntity.getAddressCity()).isEqualTo(customer.addressCity());
        assertThat(customerPersistenceEntity.getAddressState()).isEqualTo(customer.addressState());
        assertThat(customerPersistenceEntity.getAddressZipCode()).isEqualTo(customer.addressZipCode().value());
    }
}