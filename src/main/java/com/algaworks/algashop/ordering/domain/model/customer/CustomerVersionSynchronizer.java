package com.algaworks.algashop.ordering.domain.model.customer;

import com.algaworks.algashop.ordering.domain.model.VersionSynchronizer;

public class CustomerVersionSynchronizer implements VersionSynchronizer<Customer> {

    @Override
    public void synchronizeVersion(Customer entity, Long version) {
        entity.setVersion(version);
    }
}
