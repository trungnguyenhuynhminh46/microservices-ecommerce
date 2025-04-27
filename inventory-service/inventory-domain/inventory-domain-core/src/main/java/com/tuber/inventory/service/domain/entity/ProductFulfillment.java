package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.inventory.service.domain.valueobject.ProductFulfillId;
import com.tuber.inventory.service.domain.valueobject.enums.ProductFulfillStatus;

import java.util.UUID;

//TODO: Implement this class
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

    public ProductFulfillment selfValidate() {
        return this;
    }

    public ProductFulfillment selfInItialize() {
        return this;
    }
}
