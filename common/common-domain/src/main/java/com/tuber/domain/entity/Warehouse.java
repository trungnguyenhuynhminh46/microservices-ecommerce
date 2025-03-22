package com.tuber.domain.entity;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.domain.valueobject.Address;
import com.tuber.domain.valueobject.Coordinate;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.time.LocalDate;
import java.util.UUID;

public class Warehouse extends BaseEntity<UniqueUUID> {
    private String name;
    private Address address;
    private Coordinate location;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Warehouse(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        address = builder.address;
        location = builder.location;
        description = builder.description;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public Warehouse(UUID id) {
        super.setId(new UniqueUUID(id));
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UniqueUUID id;
        private String name;
        private Address address;
        private Coordinate location;
        private String description;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
            return this;
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

        public Builder description(String val) {
            description = val;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
            throw new InventoryDomainException(InventoryResponseCode.WAREHOUSE_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
    }

    protected void initializeDescription() {
        if (getDescription() == null) {
            setDescription("");
        }
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        initializeDescription();
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }
}
