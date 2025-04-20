package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.valueobject.enums.ProductFulfillStatus;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class FulfillmentHistory extends BaseEntity<UniqueUUID> {
    private String trackingId;
    private UUID orderId;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<ProductFulfillment> productFulfillments;

    private FulfillmentHistory(Builder builder) {
        super.setId(builder.id);
        trackingId = builder.trackingId;
        orderId = builder.orderId;
        createdAt = builder.createdAt;
        updatedAt = builder.updatedAt;
        productFulfillments = builder.productFulfillments;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UniqueUUID id;
        private String trackingId;
        private UUID orderId;
        private ProductFulfillStatus fulfillStatus;
        private LocalDate createdAt;
        private LocalDate updatedAt;
        private Set<ProductFulfillment> productFulfillments;

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

        public Builder trackingId(String val) {
            trackingId = val;
            return this;
        }

        public Builder orderId(UUID val) {
            orderId = val;
            return this;
        }

        public Builder fulfillStatus(ProductFulfillStatus val) {
            fulfillStatus = val;
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

        public Builder productFulfillments(Set<ProductFulfillment> val) {
            productFulfillments = val;
            return this;
        }

        public FulfillmentHistory build() {
            return new FulfillmentHistory(this);
        }
    }

    public String getTrackingId() {
        return trackingId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public Set<ProductFulfillment> getProductFulfillments() {
        return productFulfillments;
    }


}
