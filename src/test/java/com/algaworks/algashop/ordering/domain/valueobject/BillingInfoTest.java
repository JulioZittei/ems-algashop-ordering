package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BillingInfoTest {

    @Test
    void givenValidData_shouldCreateBillingInfo() {
        BillingInfo billingInfo = BillingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(billingInfo.fullName()).isEqualTo(new FullName("John", "Doe"));
        assertThat(billingInfo.document()).isEqualTo(new Document("12345678900"));
        assertThat(billingInfo.phone()).isEqualTo(new Phone("11999999999"));
        assertThat(billingInfo.address()).isNotNull();
    }

    @Test
    void givenNullFullName_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(null)
                        .document(new Document("12345678900"))
                        .phone(new Phone("11999999999"))
                        .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_FULLNAME);
    }

    @Test
    void givenNullDocument_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(null)
                        .phone(new Phone("11999999999"))
                        .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT);
    }

    @Test
    void givenNullPhone_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(new Document("12345678900"))
                        .phone(null)
                        .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_PHONE);
    }

    @Test
    void givenNullAddress_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> BillingInfo.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(new Document("12345678900"))
                        .phone(new Phone("11999999999"))
                        .address(null)
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_ADDRESS);
    }

    @Test
    void testToBuilder() {
        BillingInfo original = BillingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        BillingInfo modified = original.toBuilder()
                .fullName(new FullName("Jane", "Doe"))
                .build();

        assertThat(modified.fullName()).isEqualTo(new FullName("Jane", "Doe"));
        assertThat(modified.document()).isEqualTo(new Document("12345678900"));
        assertThat(modified.phone()).isEqualTo(new Phone("11999999999"));
    }

    @Test
    void testEqualsAndHashCode() {
        BillingInfo info1 = BillingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        BillingInfo info2 = BillingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(info1).isEqualTo(info2);
        assertThat(info1.hashCode()).isEqualTo(info2.hashCode());
    }

    @Test
    void testToString() {
        BillingInfo billingInfo = BillingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(billingInfo.toString()).contains("fullName=");
        assertThat(billingInfo.toString()).contains("document=");
        assertThat(billingInfo.toString()).contains("phone=");
        assertThat(billingInfo.toString()).contains("address=");
    }

    @Test
    void testShippingAndBillingInfoAreDifferentTypes() {
        ShippingInfo shippingInfo = ShippingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        BillingInfo billingInfo = BillingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(shippingInfo).isNotEqualTo(billingInfo);
    }
}