package com.algaworks.algashop.ordering.infrastructure.persistence.entity;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BasePersistenceEntity {

    @CreatedDate
    protected OffsetDateTime createdAt;
    @CreatedBy
    protected UUID createdByUserId;
    @LastModifiedDate
    protected OffsetDateTime lastModifiedAt;
    @LastModifiedBy
    protected UUID lasModifiedByUserId;
    @Version
    protected Long version;
}
