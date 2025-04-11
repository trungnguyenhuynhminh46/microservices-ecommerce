package com.tuber.payment.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.payment.service.domain.valueobject.enums.TransactionType;

import java.time.LocalDate;
import java.util.UUID;

public class CreditHistory extends BaseEntity<UniqueUUID> {
    private final UUID customerId;
    private final Money amount;
    private final TransactionType transactionType;
    private LocalDate createdAt;

    private CreditHistory(Builder builder) {
        super.setId(builder.id);
        customerId = builder.customerId;
        amount = builder.amount;
        transactionType = builder.transactionType;
        createdAt = builder.createdAt;
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

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public static final class Builder {
        private UniqueUUID id;
        private UUID customerId;
        private Money amount;
        private TransactionType transactionType;
        private LocalDate createdAt;

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

        public Builder transactionType(TransactionType val) {
            transactionType = val;
            return this;
        }

        public Builder createdAt(LocalDate val) {
            createdAt = val;
            return this;
        }

        public CreditHistory build() {
            return new CreditHistory(this);
        }
    }
}
