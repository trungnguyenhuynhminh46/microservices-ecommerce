package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.domain.valueobject.Money;
import com.tuber.inventory.service.domain.valueobject.ProductFulfillId;
import com.tuber.inventory.service.domain.valueobject.enums.ProductFulfillStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductFulfillment extends BaseEntity<ProductFulfillId> {
    private UUID fulfillmentHistoryId;
    private UUID orderId;
    private UUID productId;
    private UUID inventoryId;
    private String sku;
    private Money basePrice;
    private Integer quantity;
    private ProductFulfillStatus fulfillStatus;

    private ProductFulfillment(Builder builder) {
        super.setId(builder.id);
        fulfillmentHistoryId = builder.fulfillmentHistoryId;
        orderId = builder.orderId;
        productId = builder.productId;
        inventoryId = builder.inventoryId;
        sku = builder.sku;
        basePrice = builder.basePrice;
        quantity = builder.quantity;
        fulfillStatus = builder.fulfillStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductFulfillId id;
        private UUID fulfillmentHistoryId;
        private UUID orderId;
        private UUID productId;
        private UUID inventoryId;
        private String sku;
        private Money basePrice;
        private Integer quantity;
        private ProductFulfillStatus fulfillStatus;

        private Builder() {
        }

        public Builder id(ProductFulfillId val) {
            id = val;
            return this;
        }

        public Builder id(Long id) {
            this.id = new ProductFulfillId(id);
            return this;
        }

        public Builder fulfillmentHistoryId(UUID val) {
            fulfillmentHistoryId = val;
            return this;
        }

        public Builder orderId(UUID val) {
            orderId = val;
            return this;
        }

        public Builder productId(UUID val) {
            productId = val;
            return this;
        }

        public Builder inventoryId(UUID val) {
            inventoryId = val;
            return this;
        }

        public Builder sku(String val) {
            sku = val;
            return this;
        }

        public Builder basePrice(Money val) {
            basePrice = val;
            return this;
        }

        public Builder basePrice(BigDecimal amount) {
            basePrice = new Money(amount);
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder fulfillStatus(ProductFulfillStatus val) {
            fulfillStatus = val;
            return this;
        }

        public ProductFulfillment build() {
            return new ProductFulfillment(this);
        }
    }

    public UUID getFulfillmentHistoryId() {
        return fulfillmentHistoryId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getSku() {
        return sku;
    }

    public UUID getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(UUID inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Money getBasePrice() {
        return basePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductFulfillStatus getFulfillStatus() {
        return fulfillStatus;
    }

    public void setFulfillStatus(ProductFulfillStatus fulfillStatus) {
        this.fulfillStatus = fulfillStatus;
    }

    public boolean isValidForInitialization() {
        return getId() == null && getFulfillmentHistoryId() == null && getOrderId() == null
                && getProductId() != null && getInventoryId() != null && getSku() != null
                && getBasePrice() != null && getQuantity() != null && getFulfillStatus() != null;
    }

    public ProductFulfillment selfValidate() {
        if (!isValidForInitialization()) {
            throw new InventoryDomainException(InventoryResponseCode.PRODUCT_FULFILLMENT_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        return this;
    }

    public ProductFulfillment selfInitialize(
            long id,
            UUID fulfillmentHistoryId,
            UUID orderId
    ) {
        setId(new ProductFulfillId(id));
        this.fulfillmentHistoryId = fulfillmentHistoryId;
        this.orderId = orderId;
        return this;
    }
}
