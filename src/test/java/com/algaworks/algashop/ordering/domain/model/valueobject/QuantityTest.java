package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NEGATIVE_QUANTITY;
import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.VALIDATION_ERROR_MESSAGE_NULL_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class QuantityTest {

    @Test
    void givenValidInteger_shouldCreateQuantity() {
        Quantity quantity = new Quantity(5);

        assertAll(
                () -> assertThat(quantity.value()).isEqualTo(5),
                () -> assertThat(quantity.toString()).hasToString("5")
        );
    }

    @Test
    void givenZeroValue_shouldCreateQuantity() {
        Quantity quantity = new Quantity(0);

        assertThat(quantity.value()).isZero();
    }

    @Test
    void givenLargePositiveValue_shouldCreateQuantity() {
        Quantity quantity = new Quantity(Integer.MAX_VALUE);

        assertThat(quantity.value()).isEqualTo(Integer.MAX_VALUE);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -5, -10, -100, -1000, Integer.MIN_VALUE})
    void givenNegativeInteger_shouldThrowIllegalArgumentException(int negativeValue) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Quantity(negativeValue))
                .withMessage(VALIDATION_ERROR_MESSAGE_NEGATIVE_QUANTITY);
    }

    @Test
    void givenNullValue_shouldThrowNullPointerException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Quantity(null))
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_VALUE);
    }

    @Test
    void givenZeroConstant_shouldReturnZeroQuantity() {
        assertAll(
                () -> assertThat(Quantity.ZERO).isNotNull(),
                () -> assertThat(Quantity.ZERO.value()).isZero(),
                () -> assertThat(Quantity.ZERO.toString()).hasToString("0")
        );
    }

    @Test
    void testZeroConstantIsImmutable() {
        Quantity zero1 = Quantity.ZERO;
        Quantity zero2 = Quantity.ZERO;

        assertThat(zero1.value()).isZero();
        assertThat(zero2.value()).isZero();
    }

    @Test
    void givenTwoQuantities_shouldAddCorrectly() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(3);

        Quantity result = q1.add(q2);

        assertThat(result.value()).isEqualTo(8);
    }

    @Test
    void givenQuantityAndZero_shouldReturnSameQuantity() {
        Quantity quantity = new Quantity(10);

        Quantity result = quantity.add(Quantity.ZERO);

        assertThat(result).isEqualTo(quantity);
        assertThat(result.value()).isEqualTo(10);
    }

    @Test
    void givenZeroAndQuantity_shouldReturnQuantity() {
        Quantity quantity = new Quantity(7);

        Quantity result = Quantity.ZERO.add(quantity);

        assertThat(result).isEqualTo(quantity);
    }

    @Test
    void testAddIsCommutative() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(3);

        Quantity result1 = q1.add(q2);
        Quantity result2 = q2.add(q1);

        assertThat(result1).isEqualTo(result2);
        assertThat(result1.value()).isEqualTo(8);
    }

    @Test
    void testAddIsAssociative() {
        Quantity q1 = new Quantity(2);
        Quantity q2 = new Quantity(3);
        Quantity q3 = new Quantity(4);

        Quantity result1 = q1.add(q2).add(q3);
        Quantity result2 = q1.add(q2.add(q3));

        assertThat(result1).isEqualTo(result2);
        assertThat(result1.value()).isEqualTo(9);
    }

    @Test
    void givenLargeNumbers_shouldAddCorrectly() {
        int large1 = 1000000;
        int large2 = 2000000;
        Quantity q1 = new Quantity(large1);
        Quantity q2 = new Quantity(large2);

        Quantity result = q1.add(q2);

        assertThat(result.value()).isEqualTo(3000000);
    }

    @Test
    void givenNullInAdd_shouldThrowNullPointerException() {
        Quantity quantity = new Quantity(5);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> quantity.add(null));
    }

    @Test
    void testAddReturnsNewObject() {
        Quantity original = new Quantity(5);
        Quantity other = new Quantity(3);

        Quantity result = original.add(other);

        assertThat(result).isNotSameAs(original);
        assertThat(result).isNotSameAs(other);
        assertThat(original.value()).isEqualTo(5);
        assertThat(other.value()).isEqualTo(3);
    }

    @Test
    void givenEqualQuantities_shouldReturnZero() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(5);

        int result = q1.compareTo(q2);

        assertThat(result).isZero();
    }

    @Test
    void givenFirstQuantitySmaller_shouldReturnNegative() {
        Quantity smaller = new Quantity(3);
        Quantity larger = new Quantity(7);

        int result = smaller.compareTo(larger);

        assertThat(result).isNegative();
        assertThat(result).isLessThan(0);
    }

    @Test
    void givenFirstQuantityLarger_shouldReturnPositive() {
        Quantity larger = new Quantity(10);
        Quantity smaller = new Quantity(2);

        int result = larger.compareTo(smaller);

        assertThat(result).isPositive();
        assertThat(result).isGreaterThan(0);
    }

    @Test
    void givenZeroAndPositive_shouldReturnNegative() {
        Quantity zero = Quantity.ZERO;
        Quantity positive = new Quantity(1);

        int result = zero.compareTo(positive);

        assertThat(result).isNegative();
    }

    @Test
    void givenPositiveAndZero_shouldReturnPositive() {
        Quantity positive = new Quantity(1);
        Quantity zero = Quantity.ZERO;

        int result = positive.compareTo(zero);

        assertThat(result).isPositive();
    }

    @Test
    void testCompareToForSorting() {
        List<Quantity> quantities = Arrays.asList(
                new Quantity(10),
                new Quantity(1),
                new Quantity(5),
                Quantity.ZERO,
                new Quantity(3)
        );

        quantities.sort(Quantity::compareTo);

        assertThat(quantities).extracting(Quantity::value)
                .containsExactly(0, 1, 3, 5, 10);
    }

    @Test
    void givenNullInCompareTo_shouldThrowNullPointerException() {
        Quantity quantity = new Quantity(5);

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> quantity.compareTo(null));
    }

    @Test
    void givenSameValues_shouldBeEqual() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(5);

        assertAll(
                () -> assertThat(q1).isEqualTo(q2),
                () -> assertThat(q1.hashCode()).hasSameHashCodeAs(q2.hashCode())
        );
    }

    @Test
    void givenDifferentValues_shouldNotBeEqual() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(3);

        assertThat(q1).isNotEqualTo(q2);
    }

    @Test
    void testEqualsWithNull() {
        Quantity quantity = new Quantity(5);

        assertThat(quantity).isNotEqualTo(null);
    }

    @Test
    void testEqualsWithDifferentType() {
        Quantity quantity = new Quantity(5);

        assertThat(quantity).isNotEqualTo("5");
        assertThat(quantity).isNotEqualTo(5);
    }

    @Test
    void testEqualsIsConsistent() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(5);
        Quantity q3 = new Quantity(3);

        assertThat(q1.equals(q2)).isTrue();
        assertThat(q1.equals(q2)).isTrue();

        assertThat(q1.equals(q3)).isFalse();
        assertThat(q1.equals(q3)).isFalse();
    }

    @Test
    void givenQuantity_shouldReturnStringRepresentation() {
        Quantity quantity = new Quantity(123);

        assertThat(quantity.toString()).hasToString("123");
    }

    @Test
    void givenZeroQuantity_shouldReturnZeroString() {
        assertThat(Quantity.ZERO.toString()).hasToString("0");
    }

    @Test
    void givenMultipleAddOperations_shouldWorkCorrectly() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(3);
        Quantity q3 = new Quantity(2);

        Quantity result = q1.add(q2).add(q3);

        assertThat(result.value()).isEqualTo(10);
    }

    @Test
    void testBusinessScenarioAddQuantities() {
        Quantity initialCart = new Quantity(2); // 2 itens no carrinho
        Quantity addedItems = new Quantity(3);  // Adiciona 3 itens

        Quantity finalCart = initialCart.add(addedItems);

        assertThat(finalCart.value()).isEqualTo(5);
    }

    @Test
    void givenQuantityIsImmutable() {
        Quantity original = new Quantity(5);
        Integer originalValue = original.value();

        assertThat(original.value()).isEqualTo(5);
        assertThat(original.value()).isSameAs(originalValue);
    }

    @Test
    void testAllOperationsReturnNewObjects() {
        Quantity original = new Quantity(5);
        Quantity other = new Quantity(3);

        Quantity added = original.add(other);

        assertThat(added).isNotSameAs(original);
        assertThat(added).isNotSameAs(other);
    }

    @Test
    void givenBoundaryValues_shouldHandleCorrectly() {
        assertAll(
                () -> {
                    Quantity min = new Quantity(0);
                    assertThat(min.value()).isZero();
                },
                () -> {
                    Quantity max = new Quantity(Integer.MAX_VALUE);
                    assertThat(max.value()).isEqualTo(Integer.MAX_VALUE);
                }
        );
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0",
            "1, 2, 3",
            "10, 20, 30",
            "100, 200, 300",
            "0, 100, 100",
            "100, 0, 100"
    })
    void givenVariousQuantities_shouldAddCorrectly(int a, int b, int expected) {
        Quantity q1 = new Quantity(a);
        Quantity q2 = new Quantity(b);

        Quantity result = q1.add(q2);

        assertThat(result.value()).isEqualTo(expected);
    }

    @Test
    void testQuantityInCollections() {
        Quantity q1 = new Quantity(5);
        Quantity q2 = new Quantity(5);
        Quantity q3 = new Quantity(10);

        java.util.Set<Quantity> set = new java.util.HashSet<>();
        set.add(q1);
        set.add(q2);
        set.add(q3);

        assertThat(set).hasSize(2);
        assertThat(set).containsExactlyInAnyOrder(q1, q3);
    }

    @Test
    void testCompareToContract() {
        Quantity smaller = new Quantity(3);
        Quantity larger = new Quantity(7);

        int result1 = smaller.compareTo(larger);
        int result2 = larger.compareTo(smaller);

        assertThat(Integer.signum(result1)).isEqualTo(-Integer.signum(result2));

        Quantity middle = new Quantity(5);
        assertThat(smaller).isLessThan(middle);
        assertThat(middle).isLessThan(larger);
        assertThat(smaller).isLessThan(larger);
    }
}