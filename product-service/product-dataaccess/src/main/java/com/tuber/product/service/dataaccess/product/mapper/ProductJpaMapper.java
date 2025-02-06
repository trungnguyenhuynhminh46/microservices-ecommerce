package com.tuber.product.service.dataaccess.product.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.domain.valueobject.valueobject.Money;
import com.tuber.product.service.dataaccess.product.entity.ProductAttributeJpaEntity;
import com.tuber.product.service.dataaccess.product.entity.ProductJpaEntity;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductJpaMapper {
    @Autowired
    ProductAttributeJpaMapper productAttributeJpaMapper;
    public abstract Product productJpaEntityToProductEntity(ProductJpaEntity productJpaEntity);
    public abstract ProductJpaEntity productEntityToProductJpaEntity(Product product);
    protected UUID map (UniqueUUID id) {
        return id.getValue();
    }
    protected UniqueUUID map (UUID id) {
        return new UniqueUUID(id);
    }
    protected Money map(BigDecimal amount) {
        return new Money(amount);
    }
    protected BigDecimal map(Money money) {
        return money.getAmount();
    }
    protected ProductAttribute map(ProductAttributeJpaEntity productAttributeJpaEntity) {
        return productAttributeJpaMapper.productAttributeJpaEntityToProductAttributeEntity(productAttributeJpaEntity);
    }
    protected ProductAttributeJpaEntity map(ProductAttribute productAttribute) {
        return productAttributeJpaMapper.productAttributeEntityToProductAttributeJpaEntity(productAttribute);
    }
}
