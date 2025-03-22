package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public class OrderEntity extends AggregateRoot<UniqueUUID> {
    private String trackingId;
    private String buyer;
    private Set<OrderItem> orderItems;
    private Set<Voucher> vouchers;
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
}
