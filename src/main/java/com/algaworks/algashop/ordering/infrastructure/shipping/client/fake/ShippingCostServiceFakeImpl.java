package com.algaworks.algashop.ordering.infrastructure.shipping.client.fake;

import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@ConditionalOnProperty(name = "algashop.integrations.shipping.provider", havingValue = "FAKE")
public class ShippingCostServiceFakeImpl implements ShippingCostService {

    @Override
    public CalculationResult calculate(CalculationRequest request) {
        return CalculationResult.builder()
                .cost(new Money("20.00"))
                .expectedDate(LocalDate.now().plusDays(5))
                .build();
    }
}
