package com.tuber.order.service.dataaccess.order.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.dataaccess.order.entity.OrderItemJpaEntity;
import com.tuber.order.service.dataaccess.order.entity.OrderJpaEntity;
import com.tuber.order.service.dataaccess.voucher.entity.VoucherJpaEntity;
import com.tuber.order.service.dataaccess.voucher.mapper.VoucherDataAccessMapper;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.entity.OrderItem;
import com.tuber.order.service.domain.entity.Voucher;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class OrderDataAccessMapper {
    @Autowired
    OrderItemDataAccessMapper orderItemDataAccessMapper;
    @Autowired
    VoucherDataAccessMapper voucherDataAccessMapper;

    public abstract OrderEntity productJpaEntityToProductEntity(OrderJpaEntity orderJpaEntity);
    public abstract OrderJpaEntity productEntityToProductJpaEntity(OrderEntity order);

    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected Money map(BigDecimal price) {
        return new Money(price);
    }

    protected BigDecimal map(Money price) {
        return price.getAmount();
    }

    protected OrderItem map(OrderItemJpaEntity orderItem) {
        return orderItemDataAccessMapper.orderItemJpaEntityToOrderItemEntity(orderItem);
    }

    protected OrderItemJpaEntity map(OrderItem orderItem) {
        return orderItemDataAccessMapper.orderItemEntityToOrderItemJpaEntity(orderItem);
    }

    protected Voucher map(VoucherJpaEntity voucher) {
        return voucherDataAccessMapper.voucherJpaEntityToVoucherEntity(voucher);
    }

    protected VoucherJpaEntity map(Voucher voucher) {
        return voucherDataAccessMapper.voucherEntityToVoucherJpaEntity(voucher);
    }
}
