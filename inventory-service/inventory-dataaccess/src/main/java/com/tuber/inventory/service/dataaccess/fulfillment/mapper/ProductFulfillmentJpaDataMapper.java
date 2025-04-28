package com.tuber.inventory.service.dataaccess.fulfillment.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.inventory.service.dataaccess.fulfillment.entity.FulfillmentHistoryJpaEntity;
import com.tuber.inventory.service.dataaccess.fulfillment.entity.ProductFulfillmentJpaEntity;
import com.tuber.inventory.service.domain.entity.ProductFulfillment;
import com.tuber.inventory.service.domain.valueobject.ProductFulfillId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductFulfillmentJpaDataMapper {
    @Mapping(target = "fulfillmentHistoryId", source = "fulfillmentHistory.id")
    public abstract ProductFulfillment productFulfillmentJpaEntityToProductFulfillment(ProductFulfillmentJpaEntity productFulfillmentJpaEntity);

    @Mapping(target = "fulfillmentHistory", source = "fulfillmentHistoryId")
    public abstract ProductFulfillmentJpaEntity productFulfillmentToProductFulfillmentJpaEntity(ProductFulfillment productFulfillment);

    protected Long map(ProductFulfillId id) {
        return id.getValue();
    }

    protected FulfillmentHistoryJpaEntity map(UUID fulfillmentHistoryId) {
        return FulfillmentHistoryJpaEntity.builder()
                .id(fulfillmentHistoryId)
                .build();
    }

    protected BigDecimal map(Money money) {
        return money.getAmount();
    }
}
