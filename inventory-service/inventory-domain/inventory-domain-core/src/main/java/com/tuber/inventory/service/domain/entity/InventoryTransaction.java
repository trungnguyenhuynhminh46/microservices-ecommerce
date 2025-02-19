package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.InventoryTransactionType;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.time.LocalDate;
import java.util.UUID;

public class InventoryTransaction extends BaseEntity<UniqueUUID> {
    private UUID productId;
    private String sku;
    private UUID srcWarehouseId;
    private UUID destWarehouseId;
    private Integer quantity;
    private UUID creatorId;
    private InventoryTransactionType transactionType;
    private LocalDate createdDate;

    private InventoryTransaction(Builder builder) {
        super.setId(builder.id);
        productId = builder.productId;
        sku = builder.sku;
        srcWarehouseId = builder.srcWarehouseId;
        destWarehouseId = builder.destWarehouseId;
        quantity = builder.quantity;
        creatorId = builder.creatorId;
        transactionType = builder.transactionType;
        createdDate = builder.createdDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UniqueUUID id;
        private UUID productId;
        private String sku;
        private UUID srcWarehouseId;
        private UUID destWarehouseId;
        private Integer quantity;
        private UUID creatorId;
        private InventoryTransactionType transactionType;
        private LocalDate createdDate;

        private Builder() {
        }

        public Builder id(UniqueUUID val) {
            id = val;
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

        public Builder srcWarehouseId(UUID val) {
            srcWarehouseId = val;
            return this;
        }

        public Builder destWarehouseId(UUID val) {
            destWarehouseId = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder creatorId(UUID val) {
            creatorId = val;
            return this;
        }

        public Builder transactionType(InventoryTransactionType val) {
            transactionType = val;
            return this;
        }

        public Builder createdDate(LocalDate val) {
            createdDate = val;
            return this;
        }

        public InventoryTransaction build() {
            return new InventoryTransaction(this);
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

    public UUID getSrcWarehouseId() {
        return srcWarehouseId;
    }

    public void setSrcWarehouseId(UUID srcWarehouseId) {
        this.srcWarehouseId = srcWarehouseId;
    }

    public UUID getDestWarehouseId() {
        return destWarehouseId;
    }

    public void setDestWarehouseId(UUID destWarehouseId) {
        this.destWarehouseId = destWarehouseId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(UUID creatorId) {
        this.creatorId = creatorId;
    }

    public InventoryTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(InventoryTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isValidForInitialization() {
        return getId() == null && getCreatedDate() == null && getProductId() != null && getSku() != null
                && getDestWarehouseId() != null && getQuantity() != null
                && getCreatorId() != null && getTransactionType() != null;
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new IllegalArgumentException("Invalid inventory transaction initialization");
        }
    }
}
