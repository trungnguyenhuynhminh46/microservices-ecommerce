package com.tuber.product.service.domain.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.product.service.domain.dto.category.CreateProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ModifyProductCategoryCommand;
import com.tuber.product.service.domain.dto.category.ProductCategoryListResponseData;
import com.tuber.product.service.domain.dto.category.ProductCategoryResponseData;
import com.tuber.product.service.domain.entity.ProductCategory;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    public ProductCategory updateProductCategory(ModifyProductCategoryCommand data, ProductCategory productCategory) {
        productCategory.setUpdatedAt(LocalDate.now());
        this.updateProductCategoryFields(data, productCategory);
        return productCategory;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    protected abstract void updateProductCategoryFields(ModifyProductCategoryCommand data, @MappingTarget ProductCategory productCategory);

    public UUID map(UniqueUUID id) {
        return id.getValue();
    }
}
