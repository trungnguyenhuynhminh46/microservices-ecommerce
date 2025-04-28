package com.tuber.inventory.service.dataaccess.fulfillment.mapper;

import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.dataaccess.fulfillment.entity.FulfillmentHistoryJpaEntity;
import com.tuber.inventory.service.dataaccess.fulfillment.entity.ProductFulfillmentJpaEntity;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.ProductFulfillment;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class FulfillmentHistoryJpaDataMapper {
    @Autowired ProductFulfillmentJpaDataMapper productFulfillmentJpaDataMapper;

    public abstract FulfillmentHistoryJpaEntity fulfillmentHistoryToFulfillmentHistoryJpaEntity(FulfillmentHistory fulfillmentHistory);

    @AfterMapping
    protected void afterMappingToFulfillmentHistoryJpaEntity(FulfillmentHistory fulfillmentHistory, @MappingTarget FulfillmentHistoryJpaEntity fulfillmentHistoryJpaEntity) {
        fulfillmentHistoryJpaEntity.getProductFulfillments().forEach(productFulfillmentJpaEntity -> productFulfillmentJpaEntity.setFulfillmentHistory(fulfillmentHistoryJpaEntity));
    }
    public abstract FulfillmentHistory fulfillmentHistoryJpaEntityToFulfillmentHistory(FulfillmentHistoryJpaEntity fulfillmentHistoryJpaEntity);

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected BigDecimal map(Money money) {
        return money.getAmount();
    }

    protected ProductFulfillment map(ProductFulfillmentJpaEntity productFulfillmentJpaEntity) {
        return productFulfillmentJpaDataMapper.productFulfillmentJpaEntityToProductFulfillment(productFulfillmentJpaEntity);
    }

    protected ProductFulfillmentJpaEntity map(ProductFulfillment productFulfillment) {
        return productFulfillmentJpaDataMapper.productFulfillmentToProductFulfillmentJpaEntity(productFulfillment);
    }
}
