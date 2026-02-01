package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AddressTest {

    private ZipCode createValidZipCode() {
        return new ZipCode("12345");
    }

    @Test
    void given_validAddressData_shouldCreateAddress() {
        ZipCode zipCode = createValidZipCode();

        Address address = new Address(
                "Rua das Acacias",
                "123",
                "Apto 101",
                "Jardim das Flores",
                "São Paulo",
                "SP",
                zipCode
        );

        Assertions.assertThat(address.street()).isEqualTo("Rua das Acacias");
        Assertions.assertThat(address.number()).isEqualTo("123");
        Assertions.assertThat(address.complement()).isEqualTo("Apto 101");
        Assertions.assertThat(address.neighborhood()).isEqualTo("Jardim das Flores");
        Assertions.assertThat(address.city()).isEqualTo("São Paulo");
        Assertions.assertThat(address.state()).isEqualTo("SP");
        Assertions.assertThat(address.zipCode()).isEqualTo(zipCode);
    }

    @Test
    void given_validAddressWithNullComplement_shouldCreateAddress() {
        ZipCode zipCode = createValidZipCode();

        Address address = new Address(
                "Rua das Acacias",
                "123",
                null,
                "Jardim das Flores",
                "São Paulo",
                "SP",
                zipCode
        );

        Assertions.assertThat(address.complement()).isNull();
    }

    @Test
    void given_validAddressUsingBuilder_shouldCreateAddress() {
        ZipCode zipCode = createValidZipCode();

        Address address = Address.builder()
                .street("Rua das Acacias")
                .number("123")
                .complement("Casa 02")
                .neighborhood("Jardim das Flores")
                .city("São Paulo")
                .state("SP")
                .zipCode(zipCode)
                .build();

        Assertions.assertThat(address.street()).isEqualTo("Rua das Acacias");
        Assertions.assertThat(address.zipCode()).isEqualTo(zipCode);
    }

    @Nested
    class ValidationTests {

        @Test
        void given_nullStreet_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Address(
                            null,
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_blankStreet_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Address(
                            "   ",
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_nullNumber_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            null,
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_blankNumber_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "",
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_nullNeighborhood_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            null,
                            "São Paulo",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_blankNeighborhood_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            "",
                            "São Paulo",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_nullCity_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            null,
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_blankCity_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            "",
                            "SP",
                            zipCode
                    ));
        }

        @Test
        void given_nullState_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            null,
                            zipCode
                    ));
        }

        @Test
        void given_blankState_shouldThrowException() {
            ZipCode zipCode = createValidZipCode();

            Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            "",
                            zipCode
                    ));
        }

        @Test
        void given_nullZipCode_shouldThrowNullPointerException() {
            Assertions.assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Address(
                            "Rua das Acacias",
                            "123",
                            "Apto 101",
                            "Jardim das Flores",
                            "São Paulo",
                            "SP",
                            null
                    ));
        }
    }

    @Nested
    class BuilderTests {

        @Test
        void given_builderWithModification_shouldCreateNewAddress() {
            ZipCode zipCode = createValidZipCode();

            Address original = new Address(
                    "Rua das Acacias",
                    "123",
                    "Apto 101",
                    "Jardim das Flores",
                    "São Paulo",
                    "SP",
                    zipCode
            );

            Address modified = original.toBuilder()
                    .complement("Apto 201")
                    .number("456")
                    .build();

            Assertions.assertThat(modified.complement()).isEqualTo("Apto 201");
            Assertions.assertThat(modified.number()).isEqualTo("456");
            Assertions.assertThat(modified.street()).isEqualTo("Rua das Acacias"); // unchanged
            Assertions.assertThat(modified.zipCode()).isEqualTo(zipCode);
        }
    }
}