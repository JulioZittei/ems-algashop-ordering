package com.algaworks.algashop.ordering.infrastructure.persistence.customer;

import com.algaworks.algashop.ordering.infrastructure.persistence.commons.AddressEmbeddable;
import com.algaworks.algashop.ordering.infrastructure.persistence.BasePersistenceEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(of = "id")
@Table(name = "\"customer\"")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class CustomerPersistenceEntity extends BasePersistenceEntity {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String email;
    private String phone;
    private String document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;

    private AddressEmbeddable address;
    private Integer loyaltyPoints;

    @Builder
    public CustomerPersistenceEntity(UUID id, String firstName, String lastName, LocalDate birthDate, String email,
                                     String phone, String document, Boolean promotionNotificationsAllowed,
                                     Boolean archived, OffsetDateTime registeredAt, OffsetDateTime archivedAt,
                                     AddressEmbeddable address, Integer loyaltyPoints, OffsetDateTime canceledAt,
                                     UUID createdByUserId, OffsetDateTime lastModifiedAt, UUID lasModifiedByUserId,
                                     Long version) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.document = document;
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
        this.archived = archived;
        this.registeredAt = registeredAt;
        this.archivedAt = archivedAt;
        this.address = address;
        this.loyaltyPoints = loyaltyPoints;
        this.createdByUserId = createdByUserId;
        this.lastModifiedAt = lastModifiedAt;
        this.lasModifiedByUserId = lasModifiedByUserId;
        this.version = version;
    }

    public Boolean isArchived() {
        return Optional.ofNullable(archived).orElse(false);
    }

    public Boolean isPromotionNotificationsAllowed() {
        return Optional.ofNullable(promotionNotificationsAllowed).orElse(false);
    }

    public String getAddressStreet() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getStreet)
                .orElse(null);
    }

    public String getAddressNumber() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getNumber)
                .orElse(null);
    }

    public String getAddressComplement() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getComplement)
                .orElse(null);
    }

    public String getAddressNeighborhood() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getNeighborhood)
                .orElse(null);
    }

    public String getAddressCity() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getCity)
                .orElse(null);
    }

    public String getAddressState() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getState)
                .orElse(null);
    }

    public String getAddressZipCode() {
        return Optional.ofNullable(address)
                .map(AddressEmbeddable::getZipCode)
                .orElse(null);
    }
}