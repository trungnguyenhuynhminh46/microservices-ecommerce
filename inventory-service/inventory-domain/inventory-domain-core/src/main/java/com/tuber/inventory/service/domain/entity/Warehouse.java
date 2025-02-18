package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Address;
import com.tuber.domain.valueobject.Coordinate;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.time.LocalDate;
import java.util.UUID;

public class Warehouse extends BaseEntity<UniqueUUID> {
    private String name;
    private Address address;
    private Coordinate location;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Warehouse(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        address = builder.address;
        location = builder.location;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UniqueUUID id;
        private String name;
        private Address address;
        private Coordinate location;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder address(Address val) {
            address = val;
            return this;
        }

        public Builder location(Coordinate val) {
            location = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public Builder updatedAt(LocalDate val) {
            updatedAt = val;
            return this;
        }

        public Warehouse build() {
            return new Warehouse(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coordinate getLocation() {
        return location;
    }

    public void setLocation(Coordinate location) {
        this.location = location;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isValidForInitialization() {
        return getId() == null && getCreatedAt() == null && getUpdatedAt() == null
                && getName() != null && getAddress() != null && getLocation() != null;
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new IllegalArgumentException("Invalid warehouse initialization");
        }
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }
}
