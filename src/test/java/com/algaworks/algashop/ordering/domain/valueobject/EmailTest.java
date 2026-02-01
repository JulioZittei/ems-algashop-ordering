package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {


    @Test
    void given_validValue_shouldGenerateEmail() {
        Email email = new Email("email@email.com");
        Assertions.assertThat(email.value()).isEqualTo("email@email.com");
        Assertions.assertThat(email.toString()).hasToString("email@email.com");
    }

    @Test
    void given_validValueWithSpaces_shouldGenerateEmail() {
        Email email = new Email("email@email.com         ");
        Assertions.assertThat(email.value()).isEqualTo("email@email.com");
        Assertions.assertThat(email.toString()).hasToString("email@email.com");
    }

    @Test
    void given_invalidValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Email("invalid"));
    }

    @Test
    void given_blankValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Email(""));
    }

    @Test
    void given_nullValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new Email(null));
    }
}