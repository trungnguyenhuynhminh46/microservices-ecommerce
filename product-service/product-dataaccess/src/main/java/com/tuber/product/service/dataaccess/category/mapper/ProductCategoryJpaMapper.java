package com.tuber.product.service.dataaccess.category.mapper;

import com.tuber.domain.valueobject.id.UniqueStringId;
import com.tuber.product.service.dataaccess.category.entity.ProductCategoryJpaEntity;
import com.tuber.product.service.domain.entity.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductCategoryJpaMapper {
    public abstract ProductCategory productCategoryJpaEntityToProductCategoryEntity(ProductCategoryJpaEntity productCategoryJpaEntity);
    public abstract ProductCategoryJpaEntity productCategoryEntityToProductCategoryJpaEntity(ProductCategory productCategory);

    public String map(UniqueStringId id) {
        return id.getValue();
    }
    public UniqueStringId map(String id) {
        return new UniqueStringId(id);
    }
}
