package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class FulfillmentHistory extends BaseEntity<UniqueUUID> {
    private String trackingId;
    private UUID orderId;
    private OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<ProductFulfillment> productFulfillments;

    private FulfillmentHistory(Builder builder) {
        super.setId(builder.id);
        trackingId = builder.trackingId;
        orderId = builder.orderId;
        orderInventoryConfirmationStatus = builder.orderInventoryConfirmationStatus;
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
        private OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
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

        public Builder orderInventoryConfirmationStatus(OrderInventoryConfirmationStatus val) {
            orderInventoryConfirmationStatus = val;
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

    public OrderInventoryConfirmationStatus getOrderInventoryConfirmationStatus() {
        return orderInventoryConfirmationStatus;
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

    public FulfillmentHistory selfValidate() {
        return this;
    }

    public FulfillmentHistory selfInitialize() {
        return this;
    }
}
