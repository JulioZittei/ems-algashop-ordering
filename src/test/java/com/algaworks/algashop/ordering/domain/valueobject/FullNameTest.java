package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FullNameTest {

    @Test
    void given_validValue_shouldGenerateFullName() {
        FullName fullName = new FullName("Firt Name", "Last Name");
        Assertions.assertThat(fullName.toString()).hasToString("Firt Name Last Name");
        Assertions.assertThat(fullName.firstName()).isEqualTo("Firt Name");
        Assertions.assertThat(fullName.lastName()).isEqualTo("Last Name");
    }

    @Test
    void given_validValueWithSpaces_shouldGenerateFullName() {
        FullName fullName = new FullName("Firt Name    ", "Last Name    ");
        Assertions.assertThat(fullName.toString()).hasToString("Firt Name Last Name");
        Assertions.assertThat(fullName.firstName()).isEqualTo("Firt Name");
        Assertions.assertThat(fullName.lastName()).isEqualTo("Last Name");
    }

    @Test
    void given_nullValueFirstName_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new FullName(null, "Last Name"));
    }

    @Test
    void given_nullValueLastName_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new FullName("First Name", null));
    }

    @Test
    void given_blankValueFirstName_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new FullName("", "Last Name"));
    }

    @Test
    void given_blankValueLastName_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new FullName("First Name", ""));
    }
}