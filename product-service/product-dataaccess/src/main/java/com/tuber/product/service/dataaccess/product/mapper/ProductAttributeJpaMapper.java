package com.tuber.product.service.dataaccess.product.mapper;

import com.tuber.domain.valueobject.id.LongId;
import com.tuber.product.service.dataaccess.product.entity.ProductAttributeJpaEntity;
import com.tuber.product.service.domain.entity.Product;
import com.tuber.product.service.domain.entity.ProductAttribute;
import org.mapstruct.Mapping;

import java.util.UUID;

public abstract class ProductAttributeJpaMapper {
    @Mapping(target = "productId", source = "product")
    public abstract ProductAttribute productAttributeJpaEntityToProductAttributeEntity(ProductAttributeJpaEntity productAttributeJpaEntity);
    @Mapping(target = "product", source = "productId")
    public abstract ProductAttributeJpaEntity productAttributeEntityToProductAttributeJpaEntity(ProductAttribute productAttributeDTO);

    protected LongId map(Long id) {
        return new LongId(id);
    }

    protected Long map(LongId id) {
        return id.getValue();
    }

    protected Product map(UUID productId) {
        return new Product(productId);
    }

    protected UUID map(Product product) {
        return product.getId().getValue();
    }

}
