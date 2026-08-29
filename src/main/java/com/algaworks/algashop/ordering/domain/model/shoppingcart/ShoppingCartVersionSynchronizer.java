package com.algaworks.algashop.ordering.domain.model.shoppingcart;

import com.algaworks.algashop.ordering.domain.model.VersionSynchronizer;

public class ShoppingCartVersionSynchronizer implements VersionSynchronizer<ShoppingCart> {

    @Override
    public void synchronizeVersion(ShoppingCart shoppingCart, Long version) {
        shoppingCart.setVersion(version);
    }
}
