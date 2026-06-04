package com.algaworks.algashop.ordering.domain.model.entity;

public class CustomerVersionSynchronizer implements VersionSynchronizer<Customer> {

    @Override
    public void synchronizeVersion(Customer entity, Long version) {
        entity.setVersion(version);
    }
}
