package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ZipCodeTest {

    @Test
    void given_validZipCode_shouldCreateZipCode() {
        ZipCode zipCode = new ZipCode("12345");

        Assertions.assertThat(zipCode.value()).isEqualTo("12345");
        Assertions.assertThat(zipCode.toString()).hasToString("12345");
    }

    @Test
    void given_validZipCodeWithSpaces_shouldTrimAndCreateZipCode() {
        ZipCode zipCode = new ZipCode("  12345  ");

        Assertions.assertThat(zipCode.value()).isEqualTo("12345");
        Assertions.assertThat(zipCode.toString()).hasToString("12345");
    }

    @Test
    void given_nullValue_shouldThrowNullPointerException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new ZipCode(null));
    }

    @Test
    void given_blankValue_shouldThrowIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ZipCode(""));
    }

    @Test
    void given_whitespaceOnlyValue_shouldThrowIllegalArgumentException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ZipCode("   "));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1234", "123456", "1"})
    void given_invalidLengthZipCode_shouldThrowIllegalArgumentException(String invalidZipCode) {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ZipCode(invalidZipCode))
                .withMessageContaining("5");
    }
}