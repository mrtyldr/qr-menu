package com.wusly.backendmenu.core;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import org.springframework.data.domain.Persistable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.*;


@MappedSuperclass
public abstract class Aggregate<ID extends Serializable> implements Persistable<ID>{

    @Transient
    private boolean isNew = false;

    @NotNull
    @Getter
    @Id
    protected ID id;

    OffsetDateTime createdAt = OffsetDateTime.now();
    OffsetDateTime updatedAt = createdAt;

    public void setId(ID id) {
        this.id = id;
    }

    protected void markAsNew() {
        isNew = true;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ID getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aggregate<?> aggregate = (Aggregate<?>) o;
        return Objects.equals(id, aggregate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
