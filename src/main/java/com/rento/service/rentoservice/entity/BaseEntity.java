package com.rento.service.rentoservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(name = "uuid", unique = true, nullable = false, updatable = false)
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}