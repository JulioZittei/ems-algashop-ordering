package com.algaworks.algashop.ordering.domain.model.valueobject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static com.algaworks.algashop.ordering.domain.model.exception.ErrorMessages.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

class MoneyTest {

    @Test
    void givenValidStringValue_shouldCreateMoney() {
        Money money = new Money("100.50");

        assertAll(
                () -> assertThat(money.value()).isEqualTo(new BigDecimal("100.50")),
                () -> assertThat(money.toString()).hasToString("100.50"),
                () -> assertThat(money.value().scale()).isEqualTo(2)
        );
    }

    @Test
    void givenStringValueWithSpaces_shouldTrimAndCreateMoney() {
        Money money = new Money("   123.456   ");

        assertThat(money.value()).isEqualTo(new BigDecimal("123.46")); // Arredondado
        assertThat(money.toString()).hasToString("123.46");
    }

    @Test
    void givenStringIntegerValue_shouldCreateMoneyWithTwoDecimals() {
        Money money = new Money("100");

        assertThat(money.value()).isEqualTo(new BigDecimal("100.00"));
        assertThat(money.toString()).hasToString("100.00");
    }

    @Test
    void givenStringWithManyDecimals_shouldRoundHalfEven() {
        Money money1 = new Money("100.505");
        Money money2 = new Money("100.515");
        Money money3 = new Money("100.525");

        assertAll(
                () -> assertThat(money1.value()).isEqualTo(new BigDecimal("100.50")),
                () -> assertThat(money2.value()).isEqualTo(new BigDecimal("100.52")),
                () -> assertThat(money3.value()).isEqualTo(new BigDecimal("100.52"))
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    void givenBlankOrNullString_shouldThrowIllegalArgumentException(String invalidValue) {
        if (invalidValue == null) {
            assertThatExceptionOfType(NullPointerException.class)
                    .isThrownBy(() -> new Money(invalidValue))
                    .withMessageContaining("null");
        } else {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Money(invalidValue))
                    .withMessageContaining("blank");
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "123.45.67", "1,000.50", "$100.50", "10A"})
    void givenInvalidNumberString_shouldThrowIllegalArgumentException(String invalidNumber) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(invalidNumber))
                .withMessageContaining("not a number");
    }

    @Test
    void givenNegativeStringValue_shouldThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money("-100.50"))
                .withMessageContaining("negative");
    }

    @Test
    void givenValidBigDecimal_shouldCreateMoney() {
        Money money = new Money(new BigDecimal("250.75"));

        assertAll(
                () -> assertThat(money.value()).isEqualTo(new BigDecimal("250.75")),
                () -> assertThat(money.value().scale()).isEqualTo(2)
        );
    }

    @Test
    void givenBigDecimalWithMoreDecimals_shouldRoundToTwoDecimals() {

        Money money = new Money(new BigDecimal("123.456789"));

        assertThat(money.value()).isEqualTo(new BigDecimal("123.46"));
    }

    @Test
    void givenBigDecimalInteger_shouldCreateMoneyWithTwoDecimals() {
        Money money = new Money(BigDecimal.valueOf(500));

        assertThat(money.value()).isEqualTo(new BigDecimal("500.00"));
    }

    @Test
    void givenNullBigDecimal_shouldThrowNullPointerException() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money((BigDecimal) null))
                .withMessageContaining("null");
    }

    @Test
    void givenNegativeBigDecimal_shouldThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("-50.25")))
                .withMessageContaining("negative");
    }

    @Test
    void givenZeroBigDecimal_shouldCreateMoneyWithZero() {
        Money money = new Money(BigDecimal.ZERO);

        assertThat(money.value()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void givenVeryLargeNumber_shouldHandleCorrectly() {
        Money money = new Money("999999999.99");

        assertThat(money.value()).isEqualTo(new BigDecimal("999999999.99"));
    }

    @Test
    void givenScientificNotationString_shouldParseCorrectly() {
        Money money = new Money("1.23E3"); // 1230

        assertThat(money.value()).isEqualTo(new BigDecimal("1230.00"));
    }

    @Test
    void givenZeroConstant_shouldReturnZeroMoney() {

        assertAll(
                () -> assertThat(Money.ZERO).isNotNull(),
                () -> assertThat(Money.ZERO.value()).isEqualTo(new BigDecimal("0.00")),
                () -> assertThat(Money.ZERO.toString()).hasToString("0.00")
        );
    }

    @Test
    void testToStringMethod() {
        Money money = new Money("123.45");

        assertThat(money.toString()).hasToString("123.45");
    }

    @Test
    void testEqualsAndHashCode() {
        Money money1 = new Money("100.50");
        Money money2 = new Money("100.50");
        Money money3 = new Money("200.75");

        assertAll(
                () -> assertThat(money1).isEqualTo(money2),
                () -> assertThat(money1.hashCode()).hasSameHashCodeAs(money2.hashCode()),
                () -> assertThat(money1).isNotEqualTo(money3),
                () -> assertThat(money1).isNotEqualTo(null),
                () -> assertThat(money1).isNotEqualTo("not a Money object")
        );
    }

    @Test
    void testRecordComponents() {
        Money money = new Money("150.25");

        BigDecimal value = money.value();

        assertThat(value).isEqualTo(new BigDecimal("150.25"));
    }

    @Test
    void givenStringWithLeadingPlus_shouldParseCorrectly() {
        Money money = new Money("+100.50");

        assertThat(money.value()).isEqualTo(new BigDecimal("100.50"));
    }

    @ParameterizedTest
    @CsvSource({
            "0.001, 0.00",
            "0.005, 0.00",
            "0.015, 0.02",
            "0.025, 0.02",
            "0.035, 0.04",
            "100.004, 100.00",
            "100.005, 100.00",
            "100.015, 100.02"
    })
    void givenVariousDecimals_shouldRoundHalfEven(String input, String expected) {
        Money money = new Money(input);

        assertThat(money.value()).isEqualTo(new BigDecimal(expected));
    }

    @Test
    void givenBoundaryValues_shouldHandleCorrectly() {
        assertAll(
                () -> {
                    Money money = new Money("0.01");
                    assertThat(money.value()).isEqualTo(new BigDecimal("0.01"));
                },
                () -> {
                    Money money = new Money("999999999999.99");
                    assertThat(money.value()).isEqualTo(new BigDecimal("999999999999.99"));
                }
        );
    }

    @Test
    void givenNegativeString_shouldThrowWithCorrectMessage() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money("-10.50"))
                .withMessage(VALIDATION_ERROR_MESSAGE_NEGATIVE_MONEY);
    }

    @Test
    void givenNegativeBigDecimal_shouldThrowWithCorrectMessage() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money(new BigDecimal("-10.50")))
                .withMessage(VALIDATION_ERROR_MESSAGE_NEGATIVE_MONEY);
    }


    @Test
    void givenNullBigDecimal_shouldThrowWithCorrectMessage() {
        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> new Money((BigDecimal) null))
                .withMessage(VALIDATION_ERROR_MESSAGE_NULL_MONEY);
    }

    @Test
    void givenBlankString_shouldThrowWithCorrectMessage() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Money("   "))
                .withMessageContaining(VALIDATION_ERROR_MESSAGE_BLANK_NULL_MONEY);
    }

    @Test
    void givenMoneyObject_shouldBeImmutable() {
        BigDecimal original = new BigDecimal("100.50");
        Money money = new Money(original);

        original.add(new BigDecimal("50.00"));

        assertThat(money.value()).isEqualTo(new BigDecimal("100.50"));
    }

    @Test
    void givenMultipleValidCreations_shouldAllSucceed() {
        String[] values = {"1.00", "2.50", "100.00", "0.01", "999.99"};

        for (String value : values) {
            Money money = new Money(value);
            assertThat(money).isNotNull();
            assertThat(money.value().scale()).isEqualTo(2);
        }
    }

    @Test
    void givenSameValueDifferentInstances_shouldBeEqual() {
        Money money1 = new Money("100.50");
        Money money2 = new Money(new BigDecimal("100.50"));
        Money money3 = new Money("100.5");

        assertAll(
                () -> assertThat(money1).isEqualTo(money2),
                () -> assertThat(money1).isEqualTo(money3),
                () -> assertThat(money2).isEqualTo(money3)
        );
    }

    @Test
    void givenMoneyAndValidQuantity_shouldMultiplyCorrectly() {
        Money money = new Money("100.50");
        Quantity quantity = new Quantity(3);

        Money result = money.multiply(quantity);

        assertThat(result.value()).isEqualTo(new BigDecimal("301.50"));
    }

    @Test
    void givenMoneyAndQuantityOne_shouldReturnSameValue() {
        Money money = new Money("75.25");
        Quantity quantity = new Quantity(1);

        Money result = money.multiply(quantity);

        assertThat(result).isEqualTo(money);
        assertThat(result.value()).isEqualTo(new BigDecimal("75.25"));
    }

    @Test
    void givenMoneyWithDecimalsAndQuantity_shouldMultiplyAndRoundCorrectly() {
        Money money = new Money("33.33");
        Quantity quantity = new Quantity(3);

        Money result = money.multiply(quantity);

        assertThat(result.value()).isEqualTo(new BigDecimal("99.99"));
    }

    @Test
    void givenMoneyZeroAndAnyQuantity_shouldReturnZero() {
        Money money = Money.ZERO;
        Quantity quantity = new Quantity(100);

        Money result = money.multiply(quantity);

        assertThat(result).isEqualTo(Money.ZERO);
        assertThat(result.value()).isEqualTo(new BigDecimal("0.00"));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -5, -100})
    void givenQuantityLessThanOne_shouldThrowIllegalArgumentException(int invalidQuantity) {
        Money money = new Money("50.00");

        if (invalidQuantity < 0) {
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> new Quantity(invalidQuantity))
                    .withMessageContaining(VALIDATION_ERROR_MESSAGE_NEGATIVE_QUANTITY);
        } else {
            Quantity quantity = new Quantity(invalidQuantity);
            assertThatExceptionOfType(IllegalArgumentException.class)
                    .isThrownBy(() -> money.multiply(quantity))
                    .withMessageContaining("money cannot be multiplied by quantity less than 1");
        }
    }

    @Test
    void givenLargeNumbers_shouldMultiplyCorrectly() {
        Money money = new Money("999999.99");
        Quantity quantity = new Quantity(1000);

        Money result = money.multiply(quantity);

        assertThat(result.value()).isEqualTo(new BigDecimal("999999990.00"));
    }

    @Test
    void givenTwoMoneyObjects_shouldAddCorrectly() {
        Money money1 = new Money("100.50");
        Money money2 = new Money("75.25");

        Money result = money1.add(money2);

        assertThat(result.value()).isEqualTo(new BigDecimal("175.75"));
    }

    @Test
    void givenMoneyAndZero_shouldReturnSameMoney() {
        Money money = new Money("123.45");

        Money result = money.add(Money.ZERO);

        assertThat(result).isEqualTo(money);
        assertThat(result.value()).isEqualTo(new BigDecimal("123.45"));
    }

    @Test
    void givenZeroAndMoney_shouldReturnMoney() {
        Money money = new Money("99.99");

        Money result = Money.ZERO.add(money);
        assertThat(result).isEqualTo(money);
    }

    @Test
    void givenMoneyWithDecimals_shouldAddAndMaintainPrecision() {
        Money money1 = new Money("0.01");
        Money money2 = new Money("0.02");

        Money result = money1.add(money2);

        assertThat(result.value()).isEqualTo(new BigDecimal("0.03"));
    }

    @Test
    void givenLargeAmounts_shouldAddCorrectly() {
        Money money1 = new Money("500000.75");
        Money money2 = new Money("750000.25");

        Money result = money1.add(money2);

        assertThat(result.value()).isEqualTo(new BigDecimal("1250001.00"));
    }

    @Test
    void testAddIsCommutative() {
        Money money1 = new Money("100.50");
        Money money2 = new Money("75.25");

        Money result1 = money1.add(money2);
        Money result2 = money2.add(money1);

        assertThat(result1).isEqualTo(result2);
        assertThat(result1.value()).isEqualTo(new BigDecimal("175.75"));
    }

    @Test
    void testAddIsAssociative() {
        Money money1 = new Money("100.00");
        Money money2 = new Money("50.00");
        Money money3 = new Money("25.00");

        Money result1 = money1.add(money2).add(money3);
        Money result2 = money1.add(money2.add(money3));

        assertThat(result1).isEqualTo(result2);
        assertThat(result1.value()).isEqualTo(new BigDecimal("175.00"));
    }

    @Test
    void givenMoneyAndDivisor_shouldDivideCorrectly() {
        Money money = new Money("100.00");
        Money divisor = new Money("25.00");

        Money result = money.divide(divisor);

        assertThat(result.value()).isEqualTo(new BigDecimal("4.00"));
    }

    @Test
    void givenMoneyWithDecimals_shouldDivideAndRoundCorrectly() {
        Money money = new Money("100.00");
        Money divisor = new Money("3.00");

        Money result = money.divide(divisor);

        assertThat(result.value()).isEqualTo(new BigDecimal("33.33"));
    }

    @ParameterizedTest
    @CsvSource({
            "100.00, 100.00, 1.00",
            "50.00, 100.00, 0.50",
            "1.00, 2.00, 0.50",
            "0.01, 0.10, 0.10",
            "100.00, 200.00, 0.50"
    })
    void givenVariousDividendsAndDivisors_shouldDivideCorrectly(
            String dividend, String divisor, String expected) {
        Money money = new Money(dividend);
        Money divisorMoney = new Money(divisor);

        Money result = money.divide(divisorMoney);

        assertThat(result.value()).isEqualTo(new BigDecimal(expected));
    }

    @Test
    void givenSameValue_shouldReturnOne() {
        Money money = new Money("123.45");

        Money result = money.divide(money);

        assertThat(result.value()).isEqualTo(new BigDecimal("1.00"));
    }

    @Test
    void givenDivisionByLargerNumber_shouldReturnFraction() {
        Money money = new Money("10.00");
        Money divisor = new Money("100.00");

        Money result = money.divide(divisor);

        assertThat(result.value()).isEqualTo(new BigDecimal("0.10"));
    }

    @Test
    void givenZeroDividend_shouldReturnZero() {
        Money money = Money.ZERO;
        Money divisor = new Money("100.00");

        Money result = money.divide(divisor);

        assertThat(result).isEqualTo(Money.ZERO);
        assertThat(result.value()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void givenDivisionResultsInRepeatingDecimal_shouldRoundHalfEven() {
        Money money = new Money("1.00");
        Money divisor = new Money("3.00");

        Money result = money.divide(divisor);

        assertThat(result.value()).isEqualTo(new BigDecimal("0.33"));
    }

    @Test
    void givenDivisionRequiringHalfEvenRounding_shouldRoundCorrectly() {
        assertThat(new Money("1.00").divide(new Money("8.00")).value())
                .isEqualTo(new BigDecimal("0.12"));

        assertThat(new Money("1.00").divide(new Money("6.00")).value())
                .isEqualTo(new BigDecimal("0.17"));
    }

    @Test
    void givenDivisionByZeroMoney_shouldThrowArithmeticException() {
        Money money = new Money("100.00");
        Money zeroDivisor = Money.ZERO;

        assertThatExceptionOfType(ArithmeticException.class)
                .isThrownBy(() -> money.divide(zeroDivisor));
    }

    @Test
    void givenNullMoneyInAdd_shouldThrowNullPointerException() {
        Money money = new Money("100.00");

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> money.add(null));
    }

    @Test
    void givenNullMoneyInDivide_shouldThrowNullPointerException() {
        Money money = new Money("100.00");

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> money.divide(null));
    }

    @Test
    void givenNullQuantityInMultiply_shouldThrowNullPointerException() {
        Money money = new Money("100.00");

        assertThatExceptionOfType(NullPointerException.class)
                .isThrownBy(() -> money.multiply(null));
    }

    @Test
    void givenOperations_originalObjectsShouldRemainUnchanged() {
        Money original1 = new Money("100.00");
        Money original2 = new Money("50.00");
        Quantity originalQuantity = new Quantity(3);

        Money multiplied = original1.multiply(originalQuantity);
        Money added = original1.add(original2);
        Money divided = original1.divide(original2);

        assertThat(original1.value()).isEqualTo(new BigDecimal("100.00"));
        assertThat(original2.value()).isEqualTo(new BigDecimal("50.00"));
        assertThat(originalQuantity.value()).isEqualTo(3);

        assertThat(multiplied).isNotSameAs(original1);
        assertThat(added).isNotSameAs(original1);
        assertThat(divided).isNotSameAs(original1);
    }

    @Test
    void givenMultipleOperationsChained_shouldWorkCorrectly() {
        Money base = new Money("100.00");
        Quantity quantity = new Quantity(2);
        Money addend = new Money("50.00");
        Money divisor = new Money("2.00");

        Money result = base.multiply(quantity)
                .add(addend)
                .divide(divisor);

        assertThat(result.value()).isEqualTo(new BigDecimal("125.00"));
    }

    @Test
    void givenComplexBusinessCalculation_shouldCalculateCorrectly() {
        Money unitPrice = new Money("29.99");
        Quantity quantity = new Quantity(5);
        Money tax = new Money("10.00");

        Money total = unitPrice.multiply(quantity)
                .add(tax)
                .divide(new Money("1.00"))
                .add(new Money("0.00"));

        assertThat(total.value()).isEqualTo(new BigDecimal("159.95"));
    }

    @Test
    void givenVerySmallAmounts_shouldMaintainPrecision() {
        Money tiny1 = new Money("0.01");
        Money tiny2 = new Money("0.01");

        Money multiplied = tiny1.multiply(new Quantity(100));
        Money added = tiny1.add(tiny2);
        Money divided = tiny1.divide(new Money("2.00"));

        assertThat(multiplied.value()).isEqualTo(new BigDecimal("1.00"));
        assertThat(added.value()).isEqualTo(new BigDecimal("0.02"));
        assertThat(divided.value()).isEqualTo(new BigDecimal("0.00"));
    }

    @Test
    void givenMoneyOperations_shouldHandleEdgeCases() {
        assertThat(new Money("0.01").divide(new Money("0.01")).value())
                .isEqualTo(new BigDecimal("1.00"));

        assertThat(new Money("999999.99").add(new Money("0.01")).value())
                .isEqualTo(new BigDecimal("1000000.00"));
    }
}