package com.algaworks.algashop.ordering.infrastructure.persistence.disassembler;

import com.algaworks.algashop.ordering.domain.model.customer.Customer;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.CustomerPersistenceEntityTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class CustomerPersistenceEntityDisassemblerTest {

    private final CustomerPersistenceEntityDisassembler disassembler = new CustomerPersistenceEntityDisassembler();

    @Test
    void shouldConvertToDomainEntity() {
        CustomerPersistenceEntity customerPersistenceEntity = CustomerPersistenceEntityTestDataBuilder.aCustomer().build();

        Customer customer = disassembler.toDomainEntity(customerPersistenceEntity);

        assertThat(customer.id().value()).isEqualTo(customerPersistenceEntity.getId());
        assertThat(customer.fullName().firstName()).isEqualTo(customerPersistenceEntity.getFirstName());
        assertThat(customer.fullName().lastName()).isEqualTo(customerPersistenceEntity.getLastName());
        assertThat(customer.birthDate().value()).isEqualTo(customerPersistenceEntity.getBirthDate());
        assertThat(customer.document().value()).isEqualTo(customerPersistenceEntity.getDocument());
        assertThat(customer.phone().value()).isEqualTo(customerPersistenceEntity.getPhone());
        assertThat(customer.email().value()).isEqualTo(customerPersistenceEntity.getEmail());
        assertThat(customer.isArchived()).isEqualTo(customerPersistenceEntity.isArchived());
        assertThat(customer.isPromotionNotificationsAllowed()).isEqualTo(customerPersistenceEntity.isPromotionNotificationsAllowed());
        assertThat(customer.registeredAt()).isEqualTo(customerPersistenceEntity.getRegisteredAt());
        assertThat(customer.archivedAt()).isEqualTo(customerPersistenceEntity.getArchivedAt());
        assertThat(customer.loyaltyPoints().value()).isEqualTo(customerPersistenceEntity.getLoyaltyPoints());
        assertThat(customer.version()).isEqualTo(customerPersistenceEntity.getVersion());
        assertThat(customer.addressStreet()).isEqualTo(customerPersistenceEntity.getAddressStreet());
        assertThat(customer.addressNumber()).isEqualTo(customerPersistenceEntity.getAddressNumber());
        assertThat(customer.addressComplement()).isEqualTo(customerPersistenceEntity.getAddressComplement());
        assertThat(customer.addressNeighborhood()).isEqualTo(customerPersistenceEntity.getAddressNeighborhood());
        assertThat(customer.addressCity()).isEqualTo(customerPersistenceEntity.getAddressCity());
        assertThat(customer.addressState()).isEqualTo(customerPersistenceEntity.getAddressState());
        assertThat(customer.addressZipCode().value()).isEqualTo(customerPersistenceEntity.getAddressZipCode());
    }

}