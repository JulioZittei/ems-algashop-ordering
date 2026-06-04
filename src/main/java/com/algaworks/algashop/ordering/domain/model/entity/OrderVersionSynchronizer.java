package com.algaworks.algashop.ordering.domain.model.entity;

public class OrderVersionSynchronizer implements VersionSynchronizer<Order> {

    @Override
    public void synchronizeVersion(Order order, Long version) {
        order.setVersion(version);
    }
}
