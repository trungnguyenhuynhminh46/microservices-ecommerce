package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Order extends AggregateRoot<UniqueUUID> {
    private String trackingId;
    private String buyer;
    private List<OrderItem> orderItems;
    private List<Voucher> vouchers;
    private Money finalPrice;
    private OrderStatus orderStatus;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    private Order(Builder builder) {
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
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
        private List<OrderItem> orderItems;
        private List<Voucher> vouchers;
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

        public Builder orderItems(List<OrderItem> val) {
            orderItems = val;
            return this;
        }

        public Builder vouchers(List<Voucher> val) {
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

        public Order build() {
            return new Order(this);
        }
    }
}
