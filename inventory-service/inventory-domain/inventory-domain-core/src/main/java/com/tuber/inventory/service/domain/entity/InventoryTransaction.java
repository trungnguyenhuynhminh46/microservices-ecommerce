package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.enums.InventoryTransactionType;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;

import java.time.LocalDate;
import java.util.UUID;

public class InventoryTransaction extends BaseEntity<UniqueUUID> {
    private UUID productId;
    private String sku;
    private UUID srcWarehouseId;
    private UUID destWarehouseId;
    private Integer quantity;
    private String creator;
    private InventoryTransactionType transactionType;
    private String description;
    private LocalDate createdDate;

    private InventoryTransaction(Builder builder) {
        super.setId(builder.id);
        productId = builder.productId;
        sku = builder.sku;
        srcWarehouseId = builder.srcWarehouseId;
        destWarehouseId = builder.destWarehouseId;
        quantity = builder.quantity;
        creator = builder.creator;
        transactionType = builder.transactionType;
        description = builder.description;
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
        private String creator;
        private InventoryTransactionType transactionType;
        private String description;
        private LocalDate createdDate;

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

        public Builder creator(String val) {
            creator = val;
            return this;
        }

        public Builder transactionType(InventoryTransactionType val) {
            transactionType = val;
            return this;
        }

        public Builder description(String val) {
            description = val;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public InventoryTransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(InventoryTransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public void validateQuantity() {
        if (getQuantity() == null || getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity must not be smaller than 0");
        }
    }

    public void validateProperties() {
        validateQuantity();
    }

    public boolean isValidForInitialization() {
        return getId() == null && getCreatedDate() == null && getProductId() != null && getSku() != null
                && getDestWarehouseId() != null && getQuantity() != null
                && getCreator() != null && getTransactionType() != null;
    }

    public void validateForInitialization() {
        if (!isValidForInitialization()) {
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_TRANSACTION_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateProperties();
    }

    public void initialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setCreatedDate(LocalDate.now());
    }
}
