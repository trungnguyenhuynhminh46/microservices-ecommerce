package com.tuber.payment.service.dataaccess.payment.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.payment.service.dataaccess.payment.entity.PaymentJpaEntity;
import com.tuber.payment.service.domain.entity.Payment;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class PaymentDataAccessMapper {
    public abstract PaymentJpaEntity paymentToPaymentJpaEntity(Payment paymentJpaEntity);
    public abstract Payment paymentJpaEntityToPayment(PaymentJpaEntity paymentJpaEntity);
    protected BigDecimal map(Money price) {
        return price.getAmount();
    }

    protected Money map(BigDecimal price) {
        return new Money(price);
    }

    protected UUID map(UniqueUUID uniqueUUID) {
        return uniqueUUID.getValue();
    }

    protected UniqueUUID map(UUID uuid) {
        return new UniqueUUID(uuid);
    }
}
