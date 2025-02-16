package com.tuber.product.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.category.*;
import com.tuber.product.service.domain.dto.product.*;
import com.tuber.product.service.domain.dto.template.attribute.*;
import com.tuber.product.service.domain.dto.template.product.*;
import jakarta.validation.Valid;

import java.util.UUID;

public interface ProductApplicationService {
    // product category
    ApiResponse<ProductCategoryResponseData> createProductCategory(@Valid CreateProductCategoryCommand createProductCategoryCommand);
    ApiResponse<ProductCategoryResponseData> getProductCategory(@Valid GetProductCategoryQuery getProductCategoryQuery);
    ApiResponse<ProductCategoryListResponseData> getAllProductCategories();
    ApiResponse<ProductCategoryResponseData> updateProductCategory(@Valid ModifyProductCategoryCommand modifyProductCategoryCommand);
    ApiResponse<ProductCategoryResponseData> deleteProductCategory(@Valid DeleteProductCategoryCommand deleteProductCategoryCommand);
    // product
    ApiResponse<ProductResponseData> createProduct(@Valid CreateProductCommand createProductCommand);
    ApiResponse<ProductResponseData> getSingleProduct(@Valid GetProductQuery getProductQuery);
    ApiResponse<ProductsListResponseData> getAllProducts();
    ApiResponse<ProductResponseData> updateProduct(@Valid ModifyProductCommand modifyProductCommand);
    ApiResponse<ProductResponseData> deleteProduct(@Valid DeleteProductCommand deleteProductCommand);
    // template product
    ApiResponse<TemplateProductResponseData> createTemplateProduct(@Valid CreateTemplateProductCommand createProductCommand);
    ApiResponse<TemplateProductResponseData> getSingleTemplateProduct(@Valid GetTemplateProductQuery getProductQuery);
    ApiResponse<TemplateProductsListResponseData> getAllTemplateProducts();
    ApiResponse<TemplateProductResponseData> updateTemplateProduct(@Valid ModifyTemplateProductCommand modifyProductCommand);
    ApiResponse<TemplateProductResponseData> deleteTemplateProduct(@Valid DeleteTemplateProductCommand deleteProductCommand);
    // template attribute
    ApiResponse<TemplateAttributeResponseData> createTemplateAttribute(@Valid CreateTemplateAttributeCommand createTemplateAttributeCommand);
    ApiResponse<TemplateAttributeResponseData> getSingleTemplateAttribute(@Valid GetTemplateAttributeQuery getTemplateAttributeQuery);
    ApiResponse<TemplateAttributesListResponseData> getAllTemplateAttributes();
    ApiResponse<TemplateAttributeResponseData> updateTemplateAttribute(@Valid ModifyTemplateAttributeCommand modifyTemplateAttributeCommand);
    ApiResponse<TemplateAttributeResponseData> deleteTemplateAttribute(UUID templateAttributeId);
}
