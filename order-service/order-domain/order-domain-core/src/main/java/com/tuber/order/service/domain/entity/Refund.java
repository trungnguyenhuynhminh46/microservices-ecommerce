package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.valueobject.enums.RefundStatus;

import java.time.LocalDate;
import java.util.UUID;

public class Refund extends BaseEntity<UniqueUUID> {
    Order order;
    Money refundAmount;
    String reason;
    RefundStatus status;
    LocalDate createdAt;
    LocalDate updatedAt;

    private Refund(Builder builder) {
        super.setId(builder.id);
        setOrder(builder.order);
        setRefundAmount(builder.refundAmount);
        setReason(builder.reason);
        setStatus(builder.status);
        setCreatedAt(builder.createdAt);
        setUpdatedAt(builder.updatedAt);
    }

    public static Builder builder() {
        return new Builder();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Money getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Money refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public RefundStatus getStatus() {
        return status;
    }

    public void setStatus(RefundStatus status) {
        this.status = status;
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
        private Order order;
        private Money refundAmount;
        private String reason;
        private RefundStatus status;
        private LocalDate createdAt;
        private LocalDate updatedAt;

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

        public Builder order(Order val) {
            order = val;
            return this;
        }

        public Builder refundAmount(Money val) {
            refundAmount = val;
            return this;
        }

        public Builder reason(String val) {
            reason = val;
            return this;
        }

        public Builder status(RefundStatus val) {
            status = val;
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

        public Refund build() {
            return new Refund(this);
        }
    }
}
