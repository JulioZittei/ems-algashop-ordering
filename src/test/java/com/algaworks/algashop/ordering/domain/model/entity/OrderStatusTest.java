package com.algaworks.algashop.ordering.domain.model.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class OrderStatusTest {

    @Test
    void shouldVerifyIfIsChangeableTo() {
        Assertions.assertThat(OrderStatus.DRAFT.isChangeableFor(OrderStatus.PLACED)).isTrue();
        Assertions.assertThat(OrderStatus.PLACED.isChangeableFor(OrderStatus.PAID)).isTrue();
        Assertions.assertThat(OrderStatus.PAID.isChangeableFor(OrderStatus.READY)).isTrue();
        Assertions.assertThat(OrderStatus.READY.isChangeableFor(OrderStatus.CANCELED)).isTrue();

        Assertions.assertThat(OrderStatus.DRAFT.isChangeableFor(OrderStatus.CANCELED)).isTrue();
        Assertions.assertThat(OrderStatus.PLACED.isChangeableFor(OrderStatus.CANCELED)).isTrue();
        Assertions.assertThat(OrderStatus.PAID.isChangeableFor(OrderStatus.CANCELED)).isTrue();
        Assertions.assertThat(OrderStatus.READY.isChangeableFor(OrderStatus.CANCELED)).isTrue();

        Assertions.assertThat(OrderStatus.DRAFT.isChangeableFor(OrderStatus.DRAFT)).isFalse();
        Assertions.assertThat(OrderStatus.DRAFT.isChangeableFor(OrderStatus.PAID)).isFalse();
        Assertions.assertThat(OrderStatus.DRAFT.isChangeableFor(OrderStatus.READY)).isFalse();

        Assertions.assertThat(OrderStatus.PLACED.isChangeableFor(OrderStatus.PLACED)).isFalse();
        Assertions.assertThat(OrderStatus.PLACED.isChangeableFor(OrderStatus.READY)).isFalse();
        Assertions.assertThat(OrderStatus.PLACED.isChangeableFor(OrderStatus.DRAFT)).isFalse();

        Assertions.assertThat(OrderStatus.READY.isChangeableFor(OrderStatus.READY)).isFalse();
        Assertions.assertThat(OrderStatus.READY.isChangeableFor(OrderStatus.DRAFT)).isFalse();
        Assertions.assertThat(OrderStatus.READY.isChangeableFor(OrderStatus.PAID)).isFalse();
    }

    @Test
    void shouldVerifyIfIsNotChangeableTo() {
        Assertions.assertThat(OrderStatus.DRAFT.isNotChangeableFor(OrderStatus.PLACED)).isFalse();
        Assertions.assertThat(OrderStatus.PLACED.isNotChangeableFor(OrderStatus.PAID)).isFalse();
        Assertions.assertThat(OrderStatus.PAID.isNotChangeableFor(OrderStatus.READY)).isFalse();
        Assertions.assertThat(OrderStatus.READY.isNotChangeableFor(OrderStatus.CANCELED)).isFalse();

        Assertions.assertThat(OrderStatus.DRAFT.isNotChangeableFor(OrderStatus.CANCELED)).isFalse();
        Assertions.assertThat(OrderStatus.PLACED.isNotChangeableFor(OrderStatus.CANCELED)).isFalse();
        Assertions.assertThat(OrderStatus.PAID.isNotChangeableFor(OrderStatus.CANCELED)).isFalse();
        Assertions.assertThat(OrderStatus.READY.isNotChangeableFor(OrderStatus.CANCELED)).isFalse();

        Assertions.assertThat(OrderStatus.DRAFT.isNotChangeableFor(OrderStatus.DRAFT)).isTrue();
        Assertions.assertThat(OrderStatus.DRAFT.isNotChangeableFor(OrderStatus.PAID)).isTrue();
        Assertions.assertThat(OrderStatus.DRAFT.isNotChangeableFor(OrderStatus.READY)).isTrue();

        Assertions.assertThat(OrderStatus.PLACED.isNotChangeableFor(OrderStatus.PLACED)).isTrue();
        Assertions.assertThat(OrderStatus.PLACED.isNotChangeableFor(OrderStatus.READY)).isTrue();
        Assertions.assertThat(OrderStatus.PLACED.isNotChangeableFor(OrderStatus.DRAFT)).isTrue();

        Assertions.assertThat(OrderStatus.READY.isNotChangeableFor(OrderStatus.READY)).isTrue();
        Assertions.assertThat(OrderStatus.READY.isNotChangeableFor(OrderStatus.DRAFT)).isTrue();
        Assertions.assertThat(OrderStatus.READY.isNotChangeableFor(OrderStatus.PAID)).isTrue();
    }

}