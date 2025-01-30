package com.tuber.product.service.dataaccess.category.mapper;

import com.tuber.domain.valueobject.id.UniqueStringId;
import com.tuber.product.service.dataaccess.category.entity.ProductCategoryJpaEntity;
import com.tuber.product.service.domain.entity.ProductCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class ProductCategoryJpaMapper {
    @Mapping(target = "id", source = "code")
    public abstract ProductCategory productCategoryJpaEntityToProductCategoryEntity(ProductCategoryJpaEntity productCategoryJpaEntity);
    @Mapping(target = "code", source = "id")
    public abstract ProductCategoryJpaEntity productCategoryEntityToProductCategoryJpaEntity(ProductCategory productCategory);

    public String map(UniqueStringId id) {
        return id.getValue();
    }
    public UniqueStringId map(String id) {
        return new UniqueStringId(id);
    }
}
