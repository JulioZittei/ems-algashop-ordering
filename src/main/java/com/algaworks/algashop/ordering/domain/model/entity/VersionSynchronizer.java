package com.algaworks.algashop.ordering.domain.model.entity;

public interface VersionSynchronizer<T> {
    void synchronizeVersion(T entity, Long version);
}
