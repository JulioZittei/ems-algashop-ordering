package com.algaworks.algashop.ordering.infrastructure.persistence.assembler;

import com.algaworks.algashop.ordering.domain.model.entity.Order;
import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import com.algaworks.algashop.ordering.infrastructure.persistence.entity.*;
import com.algaworks.algashop.ordering.infrastructure.persistence.repository.CustomerPersistenceEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderPersistenceEntityAssemblerTest {

    @InjectMocks
    private OrderPersistenceEntityAssembler  assembler;

    @Mock
    private CustomerPersistenceEntityRepository customerPersistenceRepository;

    @BeforeEach
    void setup() {
        when(customerPersistenceRepository.getReferenceById(Mockito.any(UUID.class)))
                .then(invocation -> {
                    UUID customerId = invocation.getArgument(0, UUID.class);
                    return CustomerPersistenceEntityTestDataBuilder.aCustomer().id(customerId).build();
                });
    }

    @Test
    void shouldConvertFromDomain() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity orderPersistence = assembler.fromDomain(order);

        assertThat(orderPersistence.getId()).isEqualTo(order.id().value().toLong());
        assertThat(orderPersistence.getCustomerId()).isEqualTo(order.customerId().value());
        assertThat(orderPersistence.getTotalAmount()).isEqualTo(order.totalAmount().value());
        assertThat(orderPersistence.getTotalItems()).isEqualTo(order.totalItems().value());
        assertThat(orderPersistence.getShippingCost()).isEqualTo(order.shippingCost().value());
        assertThat(orderPersistence.getShippingExpectedDate()).isEqualTo(order.shippingExpectedDate());
        assertThat(orderPersistence.getShippingRecipientFirstName()).isEqualTo(order.shippingRecipientFullName().firstName());
        assertThat(orderPersistence.getShippingRecipientLastName()).isEqualTo(order.shippingRecipientFullName().lastName());
        assertThat(orderPersistence.getShippingRecipientDocument()).isEqualTo(order.shippingRecipientDocument().value());
        assertThat(orderPersistence.getShippingRecipientPhone()).isEqualTo(order.shippingRecipientPhone().value());
        assertThat(orderPersistence.getShippingAddressStreet()).isEqualTo(order.shippingAddressStreet());
        assertThat(orderPersistence.getShippingAddressNumber()).isEqualTo(order.shippingAddressNumber());
        assertThat(orderPersistence.getShippingAddressComplement()).isEqualTo(order.shippingAddressComplement());
        assertThat(orderPersistence.getShippingAddressNeighborhood()).isEqualTo(order.shippingAddressNeighborhood());
        assertThat(orderPersistence.getShippingAddressCity()).isEqualTo(order.shippingAddressCity());
        assertThat(orderPersistence.getShippingAddressState()).isEqualTo(order.shippingAddressState());
        assertThat(orderPersistence.getShippingAddressZipCode()).isEqualTo(order.shippingAddressZipCode().value());
        assertThat(orderPersistence.getBillingFirstName()).isEqualTo(order.billingFullName().firstName());
        assertThat(orderPersistence.getBillingLastName()).isEqualTo(order.billingFullName().lastName());
        assertThat(orderPersistence.getBillingDocument()).isEqualTo(order.billingDocument().value());
        assertThat(orderPersistence.getBillingPhone()).isEqualTo(order.billingPhone().value());
        assertThat(orderPersistence.getBillingEmail()).isEqualTo(order.billingEmail().value());
        assertThat(orderPersistence.getBillingAddressStreet()).isEqualTo(order.billingAddressStreet());
        assertThat(orderPersistence.getBillingAddressNumber()).isEqualTo(order.billingAddressNumber());
        assertThat(orderPersistence.getBillingAddressComplement()).isEqualTo(order.billingAddressComplement());
        assertThat(orderPersistence.getBillingAddressNeighborhood()).isEqualTo(order.billingAddressNeighborhood());
        assertThat(orderPersistence.getBillingAddressCity()).isEqualTo(order.billingAddressCity());
        assertThat(orderPersistence.getBillingAddressState()).isEqualTo(order.billingAddressState());
        assertThat(orderPersistence.getBillingAddressZipCode()).isEqualTo(order.billingAddressZipCode().value());
        assertThat(orderPersistence.getStatus()).isEqualTo(order.status().name());
        assertThat(orderPersistence.getPaymentMethod()).isEqualTo(order.paymentMethod().name());
        assertThat(orderPersistence.getPlacedAt()).isEqualTo(order.placedAt());
        assertThat(orderPersistence.getPaidAt()).isEqualTo(order.paidAt());
        assertThat(orderPersistence.getReadyAt()).isEqualTo(order.readyAt());
        assertThat(orderPersistence.getCanceledAt()).isEqualTo(order.canceledAt());
        assertThat(orderPersistence.getVersion()).isEqualTo(order.version());
    }

    @Test
    void shouldMerge() {
        Order order = OrderTestDataBuilder.anOrder().build();
        OrderPersistenceEntity orderPersistence = assembler.merge(new OrderPersistenceEntity(), order);

        assertThat(orderPersistence.getId()).isEqualTo(order.id().value().toLong());
        assertThat(orderPersistence.getCustomerId()).isEqualTo(order.customerId().value());
        assertThat(orderPersistence.getTotalAmount()).isEqualTo(order.totalAmount().value());
        assertThat(orderPersistence.getTotalItems()).isEqualTo(order.totalItems().value());
        assertThat(orderPersistence.getShippingCost()).isEqualTo(order.shippingCost().value());
        assertThat(orderPersistence.getShippingExpectedDate()).isEqualTo(order.shippingExpectedDate());
        assertThat(orderPersistence.getShippingRecipientFirstName()).isEqualTo(order.shippingRecipientFullName().firstName());
        assertThat(orderPersistence.getShippingRecipientLastName()).isEqualTo(order.shippingRecipientFullName().lastName());
        assertThat(orderPersistence.getShippingRecipientDocument()).isEqualTo(order.shippingRecipientDocument().value());
        assertThat(orderPersistence.getShippingRecipientPhone()).isEqualTo(order.shippingRecipientPhone().value());
        assertThat(orderPersistence.getShippingAddressStreet()).isEqualTo(order.shippingAddressStreet());
        assertThat(orderPersistence.getShippingAddressNumber()).isEqualTo(order.shippingAddressNumber());
        assertThat(orderPersistence.getShippingAddressComplement()).isEqualTo(order.shippingAddressComplement());
        assertThat(orderPersistence.getShippingAddressNeighborhood()).isEqualTo(order.shippingAddressNeighborhood());
        assertThat(orderPersistence.getShippingAddressCity()).isEqualTo(order.shippingAddressCity());
        assertThat(orderPersistence.getShippingAddressState()).isEqualTo(order.shippingAddressState());
        assertThat(orderPersistence.getShippingAddressZipCode()).isEqualTo(order.shippingAddressZipCode().value());
        assertThat(orderPersistence.getBillingFirstName()).isEqualTo(order.billingFullName().firstName());
        assertThat(orderPersistence.getBillingLastName()).isEqualTo(order.billingFullName().lastName());
        assertThat(orderPersistence.getBillingDocument()).isEqualTo(order.billingDocument().value());
        assertThat(orderPersistence.getBillingPhone()).isEqualTo(order.billingPhone().value());
        assertThat(orderPersistence.getBillingEmail()).isEqualTo(order.billingEmail().value());
        assertThat(orderPersistence.getBillingAddressStreet()).isEqualTo(order.billingAddressStreet());
        assertThat(orderPersistence.getBillingAddressNumber()).isEqualTo(order.billingAddressNumber());
        assertThat(orderPersistence.getBillingAddressComplement()).isEqualTo(order.billingAddressComplement());
        assertThat(orderPersistence.getBillingAddressNeighborhood()).isEqualTo(order.billingAddressNeighborhood());
        assertThat(orderPersistence.getBillingAddressCity()).isEqualTo(order.billingAddressCity());
        assertThat(orderPersistence.getBillingAddressState()).isEqualTo(order.billingAddressState());
        assertThat(orderPersistence.getBillingAddressZipCode()).isEqualTo(order.billingAddressZipCode().value());
        assertThat(orderPersistence.getStatus()).isEqualTo(order.status().name());
        assertThat(orderPersistence.getPaymentMethod()).isEqualTo(order.paymentMethod().name());
        assertThat(orderPersistence.getPlacedAt()).isEqualTo(order.placedAt());
        assertThat(orderPersistence.getPaidAt()).isEqualTo(order.paidAt());
        assertThat(orderPersistence.getReadyAt()).isEqualTo(order.readyAt());
        assertThat(orderPersistence.getCanceledAt()).isEqualTo(order.canceledAt());
        assertThat(orderPersistence.getVersion()).isEqualTo(order.version());
    }

    @Test
    void givenAnOrderWithNoItems_shouldRemovePersistenceEntityItems() {
        Order order = OrderTestDataBuilder.anOrder().withItems(false).build();
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder().build();

        Assertions.assertThat(order.items()).isEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();

        assembler.merge(orderPersistenceEntity, order);

        Assertions.assertThat(orderPersistenceEntity.getItems()).isEmpty();
    }

    @Test
    void givenAnOrderWithItems_shouldAddToPersistenceEntityItems() {
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .items(new HashSet<>()).build();

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isEmpty();

        assembler.merge(orderPersistenceEntity, order);

        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).hasSameSizeAs(order.items());
    }

    @Test
    void givenAnOrderWithItems_whenMerge_shouldMergeCorrectly() {
        Order order = OrderTestDataBuilder.anOrder().withItems(true).build();
        Set<OrderItemPersistenceEntity> orderItemsPersitenceEntity = order.items().stream()
                .map(assembler::fromDomain)
                .collect(Collectors.toSet());

        OrderPersistenceEntity orderPersistenceEntity = OrderPersistenceEntityTestDataBuilder.existingOrder()
                .items(orderItemsPersitenceEntity)
                .build();

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(order.items()).hasSize(2);
        Assertions.assertThat(orderPersistenceEntity.getItems()).hasSize(2);

        order.removeItem(order.items().iterator().next().id());

        Assertions.assertThat(order.items()).isNotEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(order.items()).hasSize(1);
        Assertions.assertThat(orderPersistenceEntity.getItems()).hasSize(2);

        assembler.merge(orderPersistenceEntity, order);

        Assertions.assertThat(orderPersistenceEntity.getItems()).isNotEmpty();
        Assertions.assertThat(orderPersistenceEntity.getItems()).hasSameSizeAs(order.items());
    }
}