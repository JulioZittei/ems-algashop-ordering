package com.algaworks.algashop.ordering.domain.model.order;

import com.algaworks.algashop.ordering.domain.model.VersionSynchronizer;

public class OrderVersionSynchronizer implements VersionSynchronizer<Order> {

    @Override
    public void synchronizeVersion(Order order, Long version) {
        order.setVersion(version);
    }
}
