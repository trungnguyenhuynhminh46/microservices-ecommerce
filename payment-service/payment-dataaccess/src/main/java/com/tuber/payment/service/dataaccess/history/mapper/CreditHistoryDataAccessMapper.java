package com.tuber.payment.service.dataaccess.history.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.payment.service.dataaccess.history.entity.CreditHistoryJpaEntity;
import com.tuber.payment.service.domain.entity.CreditHistory;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class CreditHistoryDataAccessMapper {
    public abstract CreditHistoryJpaEntity creditHistoryToCreditHistoryJpaEntity(CreditHistory creditHistory);
    public abstract CreditHistory creditHistoryJpaEntityToCreditHistory(CreditHistoryJpaEntity creditHistoryJpaEntity);

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
