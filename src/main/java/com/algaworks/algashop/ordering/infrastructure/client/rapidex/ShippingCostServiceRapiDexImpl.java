package com.algaworks.algashop.ordering.infrastructure.client.rapidex;

import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.commons.Money;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "algashop.integrations.shipping.provider", havingValue = "RAPIDEX")
public class ShippingCostServiceRapiDexImpl implements ShippingCostService {

    private final RapiDexAPIClient rapiDexAPIClient;

    @Override
    public CalculationResult calculate(CalculationRequest request) {
        DeliveryCostResponse response = rapiDexAPIClient.calculate(
                new DeliveryCostRequest(
                        request.origin().value(),
                        request.destination().value())
        );

        LocalDate expectedDate = LocalDate.now().plusDays(response.getEstimatatedDaysToDeliver());

        return CalculationResult.builder()
                .cost(new Money(response.getDeliveryCost()))
                .expectedDate(expectedDate)
                .build();
    }
}
