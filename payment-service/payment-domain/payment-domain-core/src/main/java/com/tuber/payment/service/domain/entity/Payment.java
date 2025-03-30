package com.tuber.payment.service.domain.entity;

import com.tuber.domain.entity.AggregateRoot;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class Payment extends AggregateRoot<UniqueUUID> {
    private final UUID orderId;
    private final UUID customerId;
    private final Money totalPrice;
    private PaymentStatus paymentStatus;
    private ZonedDateTime createdAt;

    public void initializePayment() {
        setId(new UniqueUUID(UUID.randomUUID()));
        createdAt = ZonedDateTime.now(ZoneId.of("UTC"));
    }

    public void validatePayment(List<String> failureMessages) {
        if (totalPrice == null || !totalPrice.isGreaterThanZero()) {
            failureMessages.add("Total price must be greater than zero!");
        }
    }

    public void updateStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    private Payment(Builder builder) {
        setId(builder.id);
        orderId = builder.orderId;
        customerId = builder.customerId;
        totalPrice = builder.totalPrice;
        paymentStatus = builder.paymentStatus;
        createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private UniqueUUID id;
        private UUID orderId;
        private UUID customerId;
        private Money totalPrice;
        private PaymentStatus paymentStatus;
        private ZonedDateTime createdAt;

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

        public Builder orderId(UUID val) {
            orderId = val;
            return this;
        }

        public Builder customerId(UUID val) {
            customerId = val;
            return this;
        }

        public Builder totalPrice(Money val) {
            totalPrice = val;
            return this;
        }

        public Builder paymentStatus(PaymentStatus val) {
            paymentStatus = val;
            return this;
        }

        public Builder createdAt(ZonedDateTime val) {
            createdAt = val;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
