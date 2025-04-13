package com.tuber.payment.service.dataaccess.entry.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.payment.service.dataaccess.entry.entity.CreditEntryJpaEntity;
import com.tuber.payment.service.domain.entity.CreditEntry;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class CreditEntryDataAccessMapper {
    public abstract CreditEntryJpaEntity creditEntryToCreditEntryJpaEntity(CreditEntry creditEntry);
    public abstract CreditEntry creditEntryJpaEntityToCreditEntry(CreditEntryJpaEntity creditEntryJpaEntity);

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
