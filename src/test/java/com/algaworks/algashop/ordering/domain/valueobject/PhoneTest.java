package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class PhoneTest {

    @Test
    void given_validValue_shouldGeneratePhone() {
        Phone phone = new Phone("478-256-2504");
        Assertions.assertThat(phone.value()).isEqualTo("478-256-2504");
        Assertions.assertThat(phone.toString()).hasToString("478-256-2504");
    }

    @Test
    void given_validValueWithSpaces_shouldGeneratePhone() {
        Phone phone = new Phone("478-256-2504     ");
        Assertions.assertThat(phone.value()).isEqualTo("478-256-2504");
        Assertions.assertThat(phone.toString()).hasToString("478-256-2504");
    }

    @Test
    void given_nullValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new Phone(null));
    }

    @Test
    void given_blankValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Phone(""));
    }
}