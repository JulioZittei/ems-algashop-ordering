package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class CustomerIdTest {

    @Test
    void given_initialState_shouldGeneratePhone() {
        CustomerId customerId = new CustomerId();
        Assertions.assertThat(customerId.value()).isNotNull();
        Assertions.assertThat(customerId.toString()).hasToString(customerId.toString());
    }

    @Test
    void given_uuidValue_shouldGeneratePhone() {
        CustomerId customerId = new CustomerId(UUID.randomUUID());
        Assertions.assertThat(customerId.value()).isNotNull();
        Assertions.assertThat(customerId.toString()).hasToString(customerId.toString());
    }

    @Test
    void given_nullValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new CustomerId(null));
    }

    @Test
    void given_blankValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new Phone(""));
    }
}