package com.tuber.payment.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.UUID;

public class CreditEntry extends BaseEntity<UniqueUUID> {
    private final UUID customerId;
    private Money totalCreditAmount;

    public void addCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.add(amount);
    }

    public void subtractCreditAmount(Money amount) {
        totalCreditAmount = totalCreditAmount.subtract(amount);
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Money getTotalCreditAmount() {
        return totalCreditAmount;
    }

    private CreditEntry(Builder builder) {
        setId(builder.id);
        customerId = builder.customerId;
        totalCreditAmount = builder.totalCreditAmount;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private UniqueUUID id;
        private UUID customerId;
        private Money totalCreditAmount;

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

        public Builder customerId(UUID val) {
            customerId = val;
            return this;
        }

        public Builder totalCreditAmount(Money val) {
            totalCreditAmount = val;
            return this;
        }

        public CreditEntry build() {
            return new CreditEntry(this);
        }
    }
}
