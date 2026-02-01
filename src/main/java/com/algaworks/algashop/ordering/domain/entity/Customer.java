package com.algaworks.algashop.ordering.domain.entity;

import com.algaworks.algashop.ordering.domain.exception.CustomerArchivedException;
import com.algaworks.algashop.ordering.domain.exception.ErrorMessages;
import com.algaworks.algashop.ordering.domain.valueobject.*;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;

public class Customer {

    private CustomerId id;
    private FullName fullName;
    private BirthDate birthDate;
    private Email email;
    private Phone phone;
    private Document document;
    private Boolean promotionNotificationsAllowed;
    private Boolean archived;
    private OffsetDateTime registeredAt;
    private OffsetDateTime archivedAt;
    private LoyaltyPoints loyaltyPoints;

    public Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document,
                    Boolean promotionNotificationsAllowed, Boolean archived, OffsetDateTime registeredAt,
                    OffsetDateTime archivedAt, LoyaltyPoints loyaltyPoints) {
        setId(id);
        setFullName(fullName);
        setBirthDate(birthDate);
        setEmail(email);
        setPhone(phone);
        setDocument(document);
        setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        setArchived(archived);
        setRegisteredAt(registeredAt);
        setArchivedAt(archivedAt);
        setLoyaltyPoints(loyaltyPoints);
    }

    public Customer(CustomerId id, FullName fullName, BirthDate birthDate, Email email, Phone phone, Document document,
                    Boolean promotionNotificationsAllowed, OffsetDateTime registeredAt) {
        setId(id);
        setFullName(fullName);
        setBirthDate(birthDate);
        setEmail(email);
        setPhone(phone);
        setDocument(document);
        setPromotionNotificationsAllowed(promotionNotificationsAllowed);
        setArchived(false);
        setRegisteredAt(registeredAt);
        setLoyaltyPoints(LoyaltyPoints.ZERO);
    }

    public void addLoyaltyPoints(final LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        verifyIfIsChangeable();
        setLoyaltyPoints(this.loyaltyPoints.addLoyaltyPoints(loyaltyPoints));
    }

    public void archive() {
        verifyIfIsChangeable();
        setArchived(true);
        setArchivedAt(OffsetDateTime.now());
        setFullName(new FullName("Anonymous", "Anonymous"));
        setEmail(new Email(UUID.randomUUID() + "@anonymous.com"));
        setPhone(new Phone("000-000-0000"));
        setDocument(new Document("000-00-0000"));
        setBirthDate(null);
        setPromotionNotificationsAllowed(false);
    }

    public void enablePromotionNotifications() {
        verifyIfIsChangeable();
        setPromotionNotificationsAllowed(true);
    }

    public void disablePromotionNotifications() {
        verifyIfIsChangeable();
        setPromotionNotificationsAllowed(false);
    }

    public void changeFullName(final FullName fullName) {
        verifyIfIsChangeable();
        setFullName(fullName);
    }

    public void changeEmail(final Email email) {
        verifyIfIsChangeable();
        setEmail(email);
    }

    public void changePhone(final Phone phone) {
        verifyIfIsChangeable();
        setPhone(phone);
    }

    private void setId(CustomerId id) {
        this.id = id;
    }

    public CustomerId id() {
        return id;
    }

    public FullName fullName() {
        return fullName;
    }

    public BirthDate birthDate() {
        return birthDate;
    }

    public Email email() {
        return email;
    }

    public Phone phone() {
        return phone;
    }

    public Document document() {
        return document;
    }

    public boolean isPromotionNotificationsAllowed() {
        return promotionNotificationsAllowed;
    }

    public boolean isArchived() {
        return archived;
    }

    public OffsetDateTime registeredAt() {
        return registeredAt;
    }

    public OffsetDateTime archivedAt() {
        return archivedAt;
    }

    public LoyaltyPoints loyaltyPoints() {
        return loyaltyPoints;
    }

    private void setFullName(FullName fullName) {
        Objects.requireNonNull(fullName, ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_FULLNAME);
        this.fullName = fullName;
    }

    private void setBirthDate(BirthDate birthDate) {
        if(Objects.isNull(birthDate)) {
            this.birthDate = null;
            return;
        }
        this.birthDate = birthDate;
    }

    private void setEmail(Email email) {
        Objects.requireNonNull(email, VALIDATION_ERROR_MESSAGE_NULL_EMAIL);
        this.email = email;
    }

    private void setPhone(Phone phone) {
        Objects.requireNonNull(phone, VALIDATION_ERROR_MESSAGE_NULL_PHONE);
        this.phone = phone;
    }

    private void setDocument(Document document) {
        Objects.requireNonNull(document, VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT);
        this.document = document;
    }

    private void setPromotionNotificationsAllowed(Boolean promotionNotificationsAllowed) {
        Objects.requireNonNull(promotionNotificationsAllowed);
        this.promotionNotificationsAllowed = promotionNotificationsAllowed;
    }

    private void setArchived(Boolean archived) {
        Objects.requireNonNull(archived);
        this.archived = archived;
    }

    private void setRegisteredAt(OffsetDateTime registeredAt) {
        Objects.requireNonNull(registeredAt);
        this.registeredAt = registeredAt;
    }

    private void setArchivedAt(OffsetDateTime archivedAt) {
        this.archivedAt = archivedAt;
    }

    private void setLoyaltyPoints(LoyaltyPoints loyaltyPoints) {
        Objects.requireNonNull(loyaltyPoints);
        this.loyaltyPoints = loyaltyPoints;
    }

    private void verifyIfIsChangeable() {
        if (isArchived())
            throw new CustomerArchivedException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
