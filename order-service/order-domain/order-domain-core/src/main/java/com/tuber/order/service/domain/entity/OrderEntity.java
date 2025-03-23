package com.tuber.order.service.domain.entity;

import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class OrderEntity extends AggregateRoot<UniqueUUID> {
    private String trackingId;
    private String buyer;
    private Set<OrderItem> orderItems = new HashSet<>();
    private Set<Voucher> vouchers = new HashSet<>();
    private Money finalPrice;
    private OrderStatus orderStatus;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private OrderEntity(Builder builder) {
        super.setId(builder.id);
        setTrackingId(builder.trackingId);
        setBuyer(builder.buyer);
        setOrderItems(builder.orderItems);
        setVouchers(builder.vouchers);
        setFinalPrice(builder.finalPrice);
        setOrderStatus(builder.orderStatus);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Set<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(Set<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public Money getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Money finalPrice) {
        this.finalPrice = finalPrice;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
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


    public static final class Builder {
        private UniqueUUID id;
        private String trackingId;
        private String buyer;
        private Set<OrderItem> orderItems;
        private Set<Voucher> vouchers;
        private Money finalPrice;
        private OrderStatus orderStatus;
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

        public Builder trackingId(String val) {
            trackingId = val;
            return this;
        }

        public Builder buyer(String val) {
            buyer = val;
            return this;
        }

        public Builder orderItems(Set<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder vouchers(Set<Voucher> val) {
            vouchers = val;
            return this;
        }

        public Builder finalPrice(Money val) {
            finalPrice = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
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

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }

    protected String generateTrackingId(String username) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
        String timestamp = now.format(formatter);

        Random random = new Random();
        int randomNum = 1000 + random.nextInt(9000);

        return String.format("%s_%s_%d", timestamp, username, randomNum);
    }

    protected Money calculateFinalPrice() {
        //TODO: Implement this method
        return new Money(BigDecimal.valueOf(0.0));
    }

    public boolean isValidForInitialization() {
        return getId() == null && getTrackingId() == null
                && getBuyer() != null && getOrderItems() != null
                && getFinalPrice() == null && getOrderStatus() == OrderStatus.PROCESSING
                && getCreatedAt() == null && getUpdatedAt() == null;
    }

    public void validateOrderItems() {
        if (getOrderItems() != null && getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one order item");
        }
    }

    private void initializeOrderItems() {
        long itemId = 1;
        for (OrderItem orderItem: getOrderItems()) {
            orderItem.selfValidate().selfInitialize(itemId++, getId().getValue());
        }
    }

    private void initializeVouchers() {
        if (getVouchers() == null) {
            setVouchers(new HashSet<>());
        }
    }

    public OrderEntity selfValidate() {
        if (!isValidForInitialization()) {
            throw new OrderDomainException(OrderResponseCode.ORDER_IN_WRONG_STATE_FOR_INITIALIZATION, 406);
        }
        validateOrderItems();
        return this;
    }

    public OrderEntity selfInitialize() {
        setId(new UniqueUUID(UUID.randomUUID()));
        setTrackingId(generateTrackingId(getBuyer()));
        initializeOrderItems();
        initializeVouchers();
        setFinalPrice(calculateFinalPrice());
        setCreatedAt(LocalDate.now());
        setUpdatedAt(LocalDate.now());

        return this;
    }
}
