package com.roomster.roomsterbackend.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseSecurityEntity {
    @Id
    @Column(name = "id", nullable = false, length = 50)
    private String id;

    @CreatedBy
    @Column(
            name = "created_by",
            length = 50,
            nullable = false,
            updatable = false
    )
    private String createdBy;

    @LastModifiedBy
    @Column(
            name = "modified_by",
            length = 50,
            insertable = false)
    private String modifiedBy;

    @Column(name = "created_date",
            nullable = false,
            updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "modified_date",
            insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
}
