package com.algaworks.algashop.ordering.domain.model.valueobject;

import com.algaworks.algashop.ordering.domain.model.entity.OrderTestDataBuilder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ShippingTest {

    @Test
    void givenValidData_shouldCreateShipping() {
        Shipping shipping = OrderTestDataBuilder.aShipping();

        assertThat(shipping.recipient()).isNotNull();
        assertThat(shipping.recipient().fullName()).isNotNull();
        assertThat(shipping.recipient().document()).isNotNull();
        assertThat(shipping.recipient().phone()).isNotNull();
        assertThat(shipping.address()).isNotNull();
        assertThat(shipping.cost()).isNotNull();
        assertThat(shipping.expectedDate()).isNotNull();
    }

    @Test
    void testToBuilder() {
        Shipping original = OrderTestDataBuilder.aShipping();
        Shipping altShipping = OrderTestDataBuilder.anAltShipping();

        Shipping modified = original.toBuilder()
                .recipient(altShipping.recipient())
                .build();

        assertThat(modified.recipient().fullName()).isEqualTo(altShipping.recipient().fullName());
        assertThat(modified.recipient().document()).isEqualTo(altShipping.recipient().document());
        assertThat(modified.recipient().phone()).isEqualTo(altShipping.recipient().phone());
    }

    @Test
    void testEqualsAndHashCode() {
        Shipping info1 = OrderTestDataBuilder.aShipping();

        Shipping info2 = OrderTestDataBuilder.aShipping();

        assertThat(info1).isEqualTo(info2);
        assertThat(info1.hashCode()).hasSameHashCodeAs(info2.hashCode());
    }

    @Test
    void testToString() {
        Shipping shipping = OrderTestDataBuilder.aShipping();

        assertThat(shipping.toString()).contains("fullName=");
        assertThat(shipping.toString()).contains("document=");
        assertThat(shipping.toString()).contains("phone=");
        assertThat(shipping.toString()).contains("address=");
    }
}