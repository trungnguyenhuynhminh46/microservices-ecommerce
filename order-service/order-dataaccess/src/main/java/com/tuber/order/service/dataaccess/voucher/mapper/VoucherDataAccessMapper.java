package com.tuber.order.service.dataaccess.voucher.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.dataaccess.voucher.entity.VoucherJpaEntity;
import com.tuber.order.service.domain.entity.Voucher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class VoucherDataAccessMapper {
    public abstract Voucher voucherJpaEntityToVoucherEntity(VoucherJpaEntity voucherJpaEntity);
    @Mapping(target = "ordersUsedVouchers", ignore = true)
    public abstract VoucherJpaEntity voucherEntityToVoucherJpaEntity(Voucher voucher);

    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }

    protected BigDecimal mapMoney(Money money) {
        return money.getAmount();
    }

    protected Money mapMoney(BigDecimal money) {
        return new Money(money);
    }

    protected UUID mapId(UniqueUUID id) {
        return id.getValue();
    }
}
