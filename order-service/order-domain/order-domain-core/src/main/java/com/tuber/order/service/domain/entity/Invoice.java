package com.tuber.order.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.time.LocalDate;
import java.util.UUID;

public class Invoice extends BaseEntity<UniqueUUID> {
    String invoiceNo;
    UUID orderId;
    Money totalAmount;
    LocalDate issuedAt;

    private Invoice(Builder builder) {
        super.setId(builder.id);
        invoiceNo = builder.invoiceNo;
        orderId = builder.orderId;
        totalAmount = builder.totalAmount;
        issuedAt = builder.issuedAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private UniqueUUID id;
        private String invoiceNo;
        private UUID orderId;
        private Money totalAmount;
        private LocalDate issuedAt;

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

        public Builder invoiceNo(String val) {
            invoiceNo = val;
            return this;
        }

        public Builder orderId(UUID val) {
            orderId = val;
            return this;
        }

        public Builder totalAmount(Money val) {
            totalAmount = val;
            return this;
        }

        public Builder issuedAt(LocalDate val) {
            issuedAt = val;
            return this;
        }

        public Invoice build() {
            return new Invoice(this);
        }
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Money getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Money totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDate issuedAt) {
        this.issuedAt = issuedAt;
    }
}
