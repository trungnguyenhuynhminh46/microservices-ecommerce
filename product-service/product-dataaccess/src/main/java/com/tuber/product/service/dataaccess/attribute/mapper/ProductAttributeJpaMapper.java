package com.tuber.product.service.dataaccess.attribute.mapper;

import com.tuber.domain.valueobject.id.LongId;
import com.tuber.product.service.dataaccess.attribute.entity.ProductAttributeJpaEntity;
import com.tuber.product.service.dataaccess.product.entity.ProductJpaEntity;
import com.tuber.domain.entity.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductAttributeJpaMapper {
    @Mapping(target = "productId", source = "product")
    public abstract ProductAttribute productAttributeJpaEntityToProductAttributeEntity(ProductAttributeJpaEntity productAttributeJpaEntity);
    @Mapping(target = "product", ignore = true)
    public abstract ProductAttributeJpaEntity productAttributeEntityToProductAttributeJpaEntity(ProductAttribute productAttributeDTO);

    protected LongId map(Long id) {
        return new LongId(id);
    }

    protected Long map(LongId id) {
        return id.getValue();
    }

    protected UUID map(ProductJpaEntity productJpaEntity) {
        return productJpaEntity.getId();
    }

}
