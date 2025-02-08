package com.tuber.product.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.category.*;
import com.tuber.product.service.domain.dto.product.*;

import java.util.UUID;

public interface ProductApplicationService {
    ApiResponse<ProductCategoryResponseData> createProductCategory(CreateProductCategoryCommand createProductCategoryCommand);
    ApiResponse<ProductCategoryResponseData> getProductCategory(GetProductCategoryQuery getProductCategoryQuery);
    ApiResponse<ProductCategoryListResponseData> getAllProductCategories();
    ApiResponse<ProductCategoryResponseData> updateProductCategory(ModifyProductCategoryCommand modifyProductCategoryCommand);
    ApiResponse<ProductCategoryResponseData> deleteProductCategory(DeleteProductCategoryCommand deleteProductCategoryCommand);
    ApiResponse<ProductResponseData> createProduct(CreateProductCommand createProductCommand);
    ApiResponse<ProductResponseData> getSingleProduct(GetProductQuery getProductQuery);
    ApiResponse<ProductsListResponseData> getAllProducts();
    ApiResponse<ProductResponseData> updateProduct(ModifyProductCommand modifyProductCommand);
    ApiResponse<ProductResponseData> deleteProduct(DeleteProductCommand deleteProductCommand);
}
