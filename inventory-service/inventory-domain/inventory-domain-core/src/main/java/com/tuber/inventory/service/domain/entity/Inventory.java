package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.exception.InventoryDomainException;

import java.time.LocalDate;
import java.util.UUID;

public class Inventory extends AggregateRoot<UniqueUUID> {
    private UUID productId;
    private String sku;
    private UUID warehouseId;
    private Integer stockQuantity;
    private String creator;
    private String updater;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Inventory(Builder builder) {
        super.setId(builder.id);
        productId = builder.productId;
        sku = builder.sku;
        warehouseId = builder.warehouseId;
        stockQuantity = builder.stockQuantity;
        creator = builder.creator;
        updater = builder.updater;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private UniqueUUID id;
        private UUID productId;
        private String sku;
        private UUID warehouseId;
        private Integer stockQuantity;
        private String creator;
        private String updater;
        private LocalDate createdAt;
        private LocalDate updatedAt;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
            return this;
        }

        public Builder id(UUID val) {
            id = new UniqueUUID(val);
            return this;
        }

        public Builder productId(UUID val) {
            productId = val;
            return this;
        }

        public Builder sku(String val) {
            sku = val;
            return this;
        }

        public Builder warehouseId(UUID val) {
            warehouseId = val;
            return this;
        }

        public Builder stockQuantity(Integer val) {
            stockQuantity = val;
            return this;
        }

        public Builder creator(String val) {
            creator = val;
            return this;
        }

        public Builder updater(String val) {
            updater = val;
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

        public Inventory build() {
            return new Inventory(this);
        }
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public UUID getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(UUID warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
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
        return getProductId() != null && getSku() != null
                && getWarehouseId() != null && getStockQuantity() != null
                && getCreator() != null && getUpdater() != null && getId() == null
                && getCreatedAt() == null && getUpdatedAt() == null;
    }

    public void validateStockQuantity() {
        if( getStockQuantity() == null || getStockQuantity() < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be less than 0");
        }
    }

    public void validateProperties() {
        validateStockQuantity();
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateProperties();
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
    }

    public void addStock(Integer quantity) {
        setStockQuantity(getStockQuantity() + quantity);
        setUpdatedAt(LocalDate.now());
    }

    public void removeStock(Integer quantity) {
        setStockQuantity(getStockQuantity() - quantity);
        setUpdatedAt(LocalDate.now());
    }
}
