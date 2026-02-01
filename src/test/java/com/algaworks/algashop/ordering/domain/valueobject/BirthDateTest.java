package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


class BirthDateTest {

    @Test
    void given_pastDate_shouldGenerateBirthDate() {
        BirthDate birthDate = new BirthDate(LocalDate.of(1996,3,28));
        Assertions.assertThat(birthDate.value()).isEqualTo(LocalDate.of(1996,3,28));
    }

    @Test
    void given_pastDate_shouldReturnAge() {
        BirthDate birthDate = new BirthDate(LocalDate.of(1996,3,28));
        Assertions.assertThat(birthDate.age()).isGreaterThan(0);
    }


    @Test
    void given_presentDate_shouldGenerateBirthDate() {
        BirthDate birthDate = new BirthDate(LocalDate.now());
        Assertions.assertThat(birthDate.value()).isEqualTo(LocalDate.now());
    }

    @Test
    void given_futureDate_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> new BirthDate(LocalDate.now().plusDays(1)));
    }

    @Test
    void given_null_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> new BirthDate(null));
    }
}