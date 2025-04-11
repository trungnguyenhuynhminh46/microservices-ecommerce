package com.tuber.payment.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;

import java.util.List;
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

    public void confirmHaveEnoughCredit(Payment payment, List<String> failureMessages) {
        if (payment.getTotalPrice().isGreaterThan(this.getTotalCreditAmount())) {
            failureMessages.add(String.format("Customer with id %s does not have enough credit to pay for the order with id %s",
                    this.getCustomerId(), payment.getOrderId()));
        }
    }

    public void subtractPaymentInCreditEntry(Payment payment, List<String> failureMessages) {
        this.confirmHaveEnoughCredit(payment, failureMessages);
        this.subtractCreditAmount(payment.getTotalPrice());
    }

    public void addPaymentInCreditEntry(Payment payment) {
        this.addCreditAmount(payment.getTotalPrice());
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
