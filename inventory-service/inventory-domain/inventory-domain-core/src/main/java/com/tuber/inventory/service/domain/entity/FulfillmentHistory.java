package com.tuber.inventory.service.domain.entity;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.enums.InventoryOrderStatus;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FulfillmentHistory extends BaseEntity<UniqueUUID> {
    private String trackingId;
    private UUID orderId;
    private Money totalPrice;
    private OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Set<ProductFulfillment> productFulfillments;

    private FulfillmentHistory(Builder builder) {
        super.setId(builder.id);
        trackingId = builder.trackingId;
        orderId = builder.orderId;
        totalPrice = builder.totalPrice;
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
        private Money totalPrice;
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

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public Builder totalPrice(BigDecimal amount) {
            totalPrice = new Money(amount);
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

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Money getTotalPrice() {
        return totalPrice;
    }

    public OrderInventoryConfirmationStatus getOrderInventoryConfirmationStatus() {
        return orderInventoryConfirmationStatus;
    }

    public void setOrderInventoryConfirmationStatus(OrderInventoryConfirmationStatus orderInventoryConfirmationStatus) {
        this.orderInventoryConfirmationStatus = orderInventoryConfirmationStatus;
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

    public Set<ProductFulfillment> getProductFulfillments() {
        return productFulfillments;
    }

    public boolean isValidForInitialization() {
        return getId() == null && getTrackingId() == null
                && getCreatedAt() == null && getUpdatedAt() == null
                && getOrderInventoryConfirmationStatus() != null
                && getTotalPrice() != null
                && getOrderId() != null && getProductFulfillments() != null;
    }

    public void validateProductFulfillment() {
        getProductFulfillments().forEach(ProductFulfillment::selfValidate);
    }

    public FulfillmentHistory selfValidate(InventoryOrderStatus inventoryOrderStatus, List<String> failureMessages) {
        if (!isValidForInitialization()) {
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        if (inventoryOrderStatus != InventoryOrderStatus.PAID) {
            failureMessages.add(String.format("Payment is not completed for order with orderId: %s", getOrderId()));
        }
        Money totalPrice = getProductFulfillments().stream().map(ProductFulfillment::getBasePrice).reduce(Money.ZERO, Money::add);
        if (getTotalPrice().equals(totalPrice)) {
            failureMessages.add(String.format("Price total is not correct. Recorded total price: %s, calculated total price: %s", getTotalPrice(), totalPrice));
        }
        validateProductFulfillment();
        return this;
    }

    public FulfillmentHistory selfInitialize(List<String> failureMessages) {
        setId(new UniqueUUID(UUID.randomUUID()));
        setTrackingId(generateTrackingId());
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());
        initializeProductFulfillment();
        if (!failureMessages.isEmpty()) {
            setOrderInventoryConfirmationStatus(OrderInventoryConfirmationStatus.FAILED);
        }

        return this;
    }

    protected String generateTrackingId() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String timestamp = now.format(formatter);
        return String.format("%s_%s", timestamp, orderId);
    }

    protected void initializeProductFulfillment() {
        getProductFulfillments().forEach(
                ProductFulfillment::selfInItialize
        );
    }
}
