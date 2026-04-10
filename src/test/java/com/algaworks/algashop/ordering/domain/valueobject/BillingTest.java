package com.algaworks.algashop.ordering.domain.valueobject;

import com.algaworks.algashop.ordering.domain.entity.OrderTestDataBuilder;
import org.junit.jupiter.api.Test;

import static com.algaworks.algashop.ordering.domain.exception.ErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class BillingTest {

    @Test
    void givenValidData_shouldCreateBilling() {
        Billing billing = Billing.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .email(new Email("jhondoe@email.com"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(billing.fullName()).isEqualTo(new FullName("John", "Doe"));
        assertThat(billing.document()).isEqualTo(new Document("12345678900"));
        assertThat(billing.phone()).isEqualTo(new Phone("11999999999"));
        assertThat(billing.address()).isNotNull();
    }

    @Test
    void givenNullFullName_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(null)
                        .document(new Document("12345678900"))
                        .phone(new Phone("11999999999"))
                        .email(new Email("jhondoe@email.com"))
                        .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_FULLNAME);
    }

    @Test
    void givenNullDocument_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(null)
                        .phone(new Phone("11999999999"))
                        .email(new Email("jhondoe@email.com"))
                        .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_DOCUMENT);
    }

    @Test
    void givenNullPhone_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
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
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(new Document("12345678900"))
                        .phone(new Phone("11999999999"))
                        .email(new Email("jhondoe@email.com"))
                        .address(null)
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_ADDRESS);
    }

    @Test
    void givenNullEmail_shouldThrowException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> Billing.builder()
                        .fullName(new FullName("John", "Doe"))
                        .document(new Document("12345678900"))
                        .phone(new Phone("11999999999"))
                        .email(null)
                        .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                        .build())
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_EMAIL);
    }

    @Test
    void testToBuilder() {
        Billing original = Billing.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .email(new Email("jhondoe@email.com"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        Billing modified = original.toBuilder()
                .fullName(new FullName("Jane", "Doe"))
                .build();

        assertThat(modified.fullName()).isEqualTo(new FullName("Jane", "Doe"));
        assertThat(modified.document()).isEqualTo(new Document("12345678900"));
        assertThat(modified.phone()).isEqualTo(new Phone("11999999999"));
    }

    @Test
    void testEqualsAndHashCode() {
        Billing info1 = Billing.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .email(new Email("jhondoe@email.com"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        Billing info2 = Billing.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .email(new Email("jhondoe@email.com"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(info1).isEqualTo(info2);
        assertThat(info1.hashCode()).isEqualTo(info2.hashCode());
    }

    @Test
    void testToString() {
        Billing billing = Billing.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .email(new Email("jhondoe@email.com"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(billing.toString()).contains("fullName=");
        assertThat(billing.toString()).contains("document=");
        assertThat(billing.toString()).contains("phone=");
        assertThat(billing.toString()).contains("address=");
    }

    @Test
    void testShippingAndBillingAreDifferentTypes() {
        Shipping shipping = OrderTestDataBuilder.aShipping();

        Billing billing = Billing.builder()
                .fullName(new FullName("John", "Doe"))
                .document(new Document("12345678900"))
                .phone(new Phone("11999999999"))
                .email(new Email("jhondoe@email.com"))
                .address(new Address("Street", "123", "Complement", "Neighborhood", "City", "State", new ZipCode("12345")))
                .build();

        assertThat(shipping).isNotEqualTo(billing);
    }
}