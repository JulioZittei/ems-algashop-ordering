package com.algaworks.algashop.ordering.domain.model.product;

import com.algaworks.algashop.ordering.domain.model.commons.Money;

public class ProductTestDataBuilder {

    public static final ProductId DEFAULT_PRODUCT_ID = new ProductId();

    private ProductTestDataBuilder() {}

    public static Product.ProductBuilder aProduct() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("Macbook Air M5"))
                .price(new Money("7000.00"))
                .inStock(true);
    }

    public static Product.ProductBuilder aProductUnavailable() {
        return Product.builder()
                .id(DEFAULT_PRODUCT_ID)
                .name(new ProductName("Desktop FX9000"))
                .price(new Money("3000.00"))
                .inStock(false);
    }
}
