package com.tuber.product.service.dataaccess.category.mapper;

import com.tuber.domain.valueobject.id.UniqueStringId;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.product.service.dataaccess.category.entity.ProductCategoryJpaEntity;
import com.tuber.product.service.domain.entity.ProductCategory;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductCategoryJpaMapper {
    public abstract ProductCategory productCategoryJpaEntityToProductCategoryEntity(ProductCategoryJpaEntity productCategoryJpaEntity);
    public abstract ProductCategoryJpaEntity productCategoryEntityToProductCategoryJpaEntity(ProductCategory productCategory);

    public UUID map(UniqueUUID id) {
        return id.getValue();
    }

    public UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }
}
