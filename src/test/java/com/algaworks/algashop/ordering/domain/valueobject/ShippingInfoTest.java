package com.algaworks.algashop.ordering.domain.valueobject;

import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ShippingInfoTest {

    @Test
    void givenValidData_shouldCreateShippingInfo() {
        ShippingInfo shippingInfo = ShippingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(shippingInfo.fullName()).isEqualTo(new FullName("John", "Doe"));
        assertThat(shippingInfo.document()).isEqualTo(new Document("12345678900"));
        assertThat(shippingInfo.phone()).isEqualTo(new Phone("11999999999"));
        assertThat(shippingInfo.address()).isNotNull();
    }

    @Test
    void givenNullFullName_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> ShippingInfo.builder()
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
                .isThrownBy(() -> ShippingInfo.builder()
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
                .isThrownBy(() -> ShippingInfo.builder()
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
                .isThrownBy(() -> ShippingInfo.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(new Document("12345678900"))
                        .phone(new Phone("11999999999"))
                        .address(null)
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_ADDRESS);
    }

    @Test
    void testToBuilder() {
        ShippingInfo original = ShippingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        ShippingInfo modified = original.toBuilder()
                .fullName(new FullName("Jane", "Doe"))
                .build();

        assertThat(modified.fullName()).isEqualTo(new FullName("Jane", "Doe"));
        assertThat(modified.document()).isEqualTo(new Document("12345678900"));
        assertThat(modified.phone()).isEqualTo(new Phone("11999999999"));
    }

    @Test
    void testEqualsAndHashCode() {
        ShippingInfo info1 = ShippingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        ShippingInfo info2 = ShippingInfo.builder()
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
        ShippingInfo shippingInfo = ShippingInfo.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(shippingInfo.toString()).contains("fullName=");
        assertThat(shippingInfo.toString()).contains("document=");
        assertThat(shippingInfo.toString()).contains("phone=");
        assertThat(shippingInfo.toString()).contains("address=");
    }
}