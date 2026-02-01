package com.algaworks.algashop.ordering.domain.valueobject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class LoyaltyPointsTest {

    @Test
    void given_positiveValue_shouldGenerateLoayltyPoints() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(10);
        Assertions.assertThat(loyaltyPoints.value()).isEqualTo(10);
    }

    @Test
    void given_zeroValue_shouldGenerateLoyaltyPoints() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(0);
        Assertions.assertThat(loyaltyPoints.value()).isZero();
    }

    @Test
    void given_initialState_shouldGenerateLoyaltyPoints() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints();
        Assertions.assertThat(loyaltyPoints.value()).isZero();
    }

    @Test
    void given_positiveValue_shouldAddValue() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(5);
        loyaltyPoints = loyaltyPoints.addLoyaltyPoints(10);
        Assertions.assertThat(loyaltyPoints.value()).isEqualTo(15);
    }

    @Test
    void given_negativeValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> {
                    new LoyaltyPoints(-10);
                });
    }

    @Test
    void given_nullValue_shouldGenerateException() {
        Assertions.assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(()-> {
                    new LoyaltyPoints(null);
                });
    }

    @Test
    void given_zeroValue_whenAddLoayltyPoints_shouldGenerateException() {
        LoyaltyPoints loyaltyPoints = new LoyaltyPoints(0);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> loyaltyPoints.addLoyaltyPoints(0));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> loyaltyPoints.addLoyaltyPoints(loyaltyPoints));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> loyaltyPoints.addLoyaltyPoints(LoyaltyPoints.ZERO));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(()-> loyaltyPoints.addLoyaltyPoints(new LoyaltyPoints()));
    }

}