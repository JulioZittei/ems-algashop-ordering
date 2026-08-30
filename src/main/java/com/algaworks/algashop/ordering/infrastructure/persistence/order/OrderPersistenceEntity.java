package com.algaworks.algashop.ordering.infrastructure.persistence.order;


import com.algaworks.algashop.ordering.infrastructure.persistence.BasePersistenceEntity;
import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.customer.CustomerPersistenceEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = "id")
@Table(name = "\"order\"")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class OrderPersistenceEntity extends BasePersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @JoinColumn
    @ManyToOne(optional = false)
    private CustomerPersistenceEntity customer;

    private BigDecimal totalAmount;
    private Integer totalItems;
    private String status;
    private String paymentMethod;

    @Embedded
    private ShippingEmbeddable shipping;

    @Embedded
    private BillingEmbeddable billing;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItemPersistenceEntity> items = new HashSet<>();

    private OffsetDateTime placedAt;
    private OffsetDateTime paidAt;
    private OffsetDateTime readyAt;
    private OffsetDateTime canceledAt;

    @Builder
    public OrderPersistenceEntity(Long id, CustomerPersistenceEntity customer, BigDecimal totalAmount, Integer totalItems, String status,
                                  String paymentMethod, ShippingEmbeddable shipping, BillingEmbeddable billing,
                                  Set<OrderItemPersistenceEntity> items, OffsetDateTime placedAt, OffsetDateTime paidAt,
                                  OffsetDateTime readyAt, OffsetDateTime canceledAt, UUID createdByUserId,
                                  OffsetDateTime lastModifiedAt, UUID lasModifiedByUserId, Long version) {
        this.id = id;
        this.customer = customer;
        this.totalAmount = totalAmount;
        this.totalItems = totalItems;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.shipping = shipping;
        this.billing = billing;
        this.replaceItems(items);
        this.placedAt = placedAt;
        this.paidAt = paidAt;
        this.readyAt = readyAt;
        this.canceledAt = canceledAt;
        this.createdByUserId = createdByUserId;
        this.lastModifiedAt = lastModifiedAt;
        this.lasModifiedByUserId = lasModifiedByUserId;
        this.version = version;
    }

    public void replaceItems(Set<OrderItemPersistenceEntity> items) {
        if (Objects.isNull(this.items)) {
            setItems(new HashSet<>());
        } else {
            this.items.clear();
        }

        if (Objects.isNull(items) || items.isEmpty()) {
            return;
        }

        items.forEach(this::addItem);
    }

    public void addItem(OrderItemPersistenceEntity item) {
        if (Objects.isNull(item)) {
            return;
        }

        item.setOrder(this);
        items.add(item);
    }

    public UUID getCustomerId() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getId)
                .orElse(null);
    }

    public String getCustomerFirstName() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getFirstName)
                .orElse(null);
    }

    public String getCustomerLastName() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getLastName)
                .orElse(null);
    }

    public LocalDate getCustomerBirthDate() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getBirthDate)
                .orElse(null);
    }

    public String getCustomerDocument() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getDocument)
                .orElse(null);
    }

    public String getCustomerEmail() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getEmail)
                .orElse(null);
    }

    public String getCustomerPhone() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getPhone)
                .orElse(null);
    }

    public boolean isCustomerPromotionNotificationAllowed() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::isPromotionNotificationsAllowed)
                .orElse(false);
    }

    public boolean isCustomerArchived() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::isArchived)
                .orElse(false);
    }

    public OffsetDateTime getCustomerRegisteredAt() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getRegisteredAt)
                .orElse(null);
    }

    public OffsetDateTime getCustomerArchivedAt() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getArchivedAt)
                .orElse(null);
    }

    public String getCustomerAddressStreet() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getStreet)
                .orElse(null);
    }

    public String getCustomerAddressNumber() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getNumber)
                .orElse(null);
    }

    public String getCustomerAddressComplement() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getComplement)
                .orElse(null);
    }

    public String getCustomerAddressNeighborhood() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getNeighborhood)
                .orElse(null);
    }

    public String getCustomerAddressCity() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getCity)
                .orElse(null);
    }

    public String getCustomerAddressState() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getState)
                .orElse(null);
    }

    public String getCustomerAddressZipCode() {
        return Optional.ofNullable(customer)
                .map(CustomerPersistenceEntity::getAddress)
                .map(AddressEmbeddable::getZipCode)
                .orElse(null);
    }

    public String getBillingFirstName() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getFirstName)
                .orElse(null);
    }

    public String getBillingLastName() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getLastName)
                .orElse(null);
    }

    public String getBillingDocument() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getDocument)
                .orElse(null);
    }

    public String getBillingPhone() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getPhone)
                .orElse(null);
    }

    public String getBillingEmail() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getEmail)
                .orElse(null);
    }

    public String getBillingAddressStreet() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getStreet)
                .orElse(null);
    }

    public String getBillingAddressNumber() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getNumber)
                .orElse(null);
    }

    public String getBillingAddressComplement() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getComplement)
                .orElse(null);
    }

    public String getBillingAddressNeighborhood() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getNeighborhood)
                .orElse(null);
    }

    public String getBillingAddressCity() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getCity)
                .orElse(null);
    }

    public String getBillingAddressState() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getState)
                .orElse(null);
    }

    public String getBillingAddressZipCode() {
        return Optional.ofNullable(billing)
                .map(BillingEmbeddable::getAddress)
                .map(AddressEmbeddable::getZipCode)
                .orElse(null);
    }

    public BigDecimal getShippingCost() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getCost)
                .orElse(null);
    }

    public LocalDate getShippingExpectedDate() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getExpectedDate)
                .orElse(null);
    }

    public String getShippingRecipientFirstName() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getRecipient)
                .map(RecipientEmbeddable::getFirstName)
                .orElse(null);
    }

    public String getShippingRecipientLastName() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getRecipient)
                .map(RecipientEmbeddable::getLastName)
                .orElse(null);
    }

    public String getShippingRecipientDocument() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getRecipient)
                .map(RecipientEmbeddable::getDocument)
                .orElse(null);
    }

    public String getShippingRecipientPhone() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getRecipient)
                .map(RecipientEmbeddable::getPhone)
                .orElse(null);
    }

    public String getShippingAddressStreet() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getStreet)
                .orElse(null);
    }

    public String getShippingAddressNumber() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getNumber)
                .orElse(null);
    }

    public String getShippingAddressComplement() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getComplement)
                .orElse(null);
    }

    public String getShippingAddressNeighborhood() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getNeighborhood)
                .orElse(null);
    }

    public String getShippingAddressCity() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getCity)
                .orElse(null);
    }

    public String getShippingAddressState() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getState)
                .orElse(null);
    }

    public String getShippingAddressZipCode() {
        return Optional.ofNullable(shipping)
                .map(ShippingEmbeddable::getAddress)
                .map(AddressEmbeddable::getZipCode)
                .orElse(null);
    }
}