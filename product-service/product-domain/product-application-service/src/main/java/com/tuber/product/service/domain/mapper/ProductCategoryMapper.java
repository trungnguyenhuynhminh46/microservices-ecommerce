package com.tuber.product.service.domain.mapper;

import com.tuber.product.service.domain.dto.category.CreateProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryListResponseData;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class ProductCategoryMapper {
    public abstract ProductCategory createProductCategoryCommandToProductCategory(CreateProductCategoryCommand createProductCategoryCommand);

    public abstract ProductCategoryResponseData productCategoryToProductCategoryResponseData(ProductCategory productCategory);

    public ProductCategoryListResponseData productCategoryListToProductCategoryListResponseData(Set<ProductCategory> productCategories) {
        List<ProductCategoryResponseData> productCategoryResponseDataList = productCategories.stream()
                .map(this::productCategoryToProductCategoryResponseData)
                .toList();
        Integer total = productCategoryResponseDataList.size();
        return ProductCategoryListResponseData.builder()
                .productCategories(productCategoryResponseDataList)
                .total(total)
                .build();
    }
}
