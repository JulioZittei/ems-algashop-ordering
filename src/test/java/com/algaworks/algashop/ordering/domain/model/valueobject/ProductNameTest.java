package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ProductNameTest {

    @Test
    void givenValidName_shouldCreateProductName() {
        ProductName productName = new ProductName("Smartphone XYZ");
        assertThat(productName.value()).isEqualTo("Smartphone XYZ");
    }

    @Test
    void givenNameWithSpaces_shouldTrim() {
        ProductName productName = new ProductName("  Laptop  ");
        assertThat(productName.value()).isEqualTo("Laptop");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"", "   ", "\t", "\n"})
    void givenBlankOrNull_shouldThrowException(String invalidValue) {
       if (invalidValue == null ) {
           assertThatExceptionOfType(NullPointerException.class)
                   .isThrownBy(() -> new ProductName(invalidValue));
       } else {
           assertThatExceptionOfType(IllegalArgumentException.class)
                   .isThrownBy(() -> new ProductName(invalidValue));
       }
    }

    @Test
    void testToString() {
        ProductName productName = new ProductName("Monitor");
        assertThat(productName.toString()).hasToString("Monitor");
    }

    @Test
    void testEqualsAndHashCode() {
        ProductName name1 = new ProductName("Webcam");
        ProductName name2 = new ProductName("Webcam");
        ProductName name3 = new ProductName("Mouse");

        assertThat(name1).isEqualTo(name2);
        assertThat(name1.hashCode()).hasSameHashCodeAs(name2.hashCode());
        assertThat(name1).isNotEqualTo(name3);
    }

    @Test
    void testCaseSensitiveEquality() {
        ProductName lower = new ProductName("mouse");
        ProductName upper = new ProductName("MOUSE");
        assertThat(lower).isNotEqualTo(upper);
    }

    @Test
    void testInHashSet() {
        ProductName name1 = new ProductName("SSD");
        ProductName name2 = new ProductName("SSD");
        ProductName name3 = new ProductName("HD");

        HashSet<ProductName> set = new HashSet<>();
        set.add(name1);
        set.add(name2);
        set.add(name3);

        assertThat(set).hasSize(2);
    }
}