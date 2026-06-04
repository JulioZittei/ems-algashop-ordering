package com.algaworks.algashop.ordering.domain.model.entity;

public class ShoppingCartVersionSynchronizer implements VersionSynchronizer<ShoppingCart> {

    @Override
    public void synchronizeVersion(ShoppingCart shoppingCart, Long version) {
        shoppingCart.setVersion(version);
    }
}
