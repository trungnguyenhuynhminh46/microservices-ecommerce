package com.tuber.product.service.domain.mapper;

import com.tuber.product.service.domain.dto.category.CreateProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProductCategoryMapper {
    public abstract ProductCategory createProductCategoryCommandToProductCategory(CreateProductCategoryCommand createProductCategoryCommand);
    public abstract ProductCategoryResponseData productCategoryToProductCategoryResponseData(ProductCategory productCategory);
}
