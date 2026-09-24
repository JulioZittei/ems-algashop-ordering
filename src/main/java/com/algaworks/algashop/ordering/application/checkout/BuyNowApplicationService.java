package com.algaworks.algashop.ordering.application.checkout;

import com.algaworks.algashop.ordering.application.ApplicationService;
import com.algaworks.algashop.ordering.domain.model.order.BuyNowService;
import com.algaworks.algashop.ordering.domain.model.order.Orders;
import com.algaworks.algashop.ordering.domain.model.order.shipping.OriginAddressService;
import com.algaworks.algashop.ordering.domain.model.order.shipping.ShippingCostService;
import com.algaworks.algashop.ordering.domain.model.product.ProductCatalogService;
import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
public class BuyNowApplicationService {

    private final BuyNowService buyNowService;
    private final ProductCatalogService productCatalogService;
    private final ShippingCostService shippingCostService;
    private final OriginAddressService originAddressService;
    private final Orders orders;

}
