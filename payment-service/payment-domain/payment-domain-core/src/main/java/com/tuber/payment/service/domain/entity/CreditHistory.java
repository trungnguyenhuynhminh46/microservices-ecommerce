package com.tuber.payment.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.UUID;

public class CreditHistory extends BaseEntity<UniqueUUID> {
    private final UUID customerId;
    private final Money amount;
    private final String transactionType;

    private CreditHistory(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        amount = builder.amount;
        transactionType = builder.transactionType;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public Money getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public static final class Builder {
        private UniqueUUID id;
        private UUID customerId;
        private Money amount;
        private String transactionType;

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

        public Builder amount(Money val) {
            amount = val;
            return this;
        }

        public Builder transactionType(String val) {
            transactionType = val;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}
