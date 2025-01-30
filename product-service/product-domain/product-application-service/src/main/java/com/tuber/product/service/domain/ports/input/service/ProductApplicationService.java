package com.tuber.product.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.category.*;

public interface ProductApplicationService {
    ApiResponse<ProductCategoryResponseData> createProductCategory(CreateProductCategoryCommand createProductCategoryCommand);
    ApiResponse<ProductCategoryResponseData> getProductCategory(GetProductCategoryQuery getProductCategoryQuery);
    ApiResponse<ProductCategoryListResponseData> getAllProductCategories();
    ApiResponse<ProductCategoryResponseData> updateProductCategory(ModifyProductCategoryCommand modifyProductCategoryCommand);
    ApiResponse<ProductCategoryResponseData> deleteProductCategory(DeleteProductCategoryCommand deleteProductCategoryCommand);
}
