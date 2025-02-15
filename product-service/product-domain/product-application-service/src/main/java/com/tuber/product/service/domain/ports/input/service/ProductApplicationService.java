package com.tuber.product.service.domain.ports.input.service;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.category.*;
import com.tuber.product.service.domain.dto.product.*;
import com.tuber.product.service.domain.dto.template.attribute.*;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

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
    ApiResponse<ProductResponseData> createTemplateProduct(@Valid CreateProductCommand createProductCommand);
    ApiResponse<ProductResponseData> getSingleTemplateProduct(@Valid GetProductQuery getProductQuery);
    ApiResponse<ProductsListResponseData> getAllTemplateProducts();
    ApiResponse<ProductResponseData> updateTemplateProduct(@Valid ModifyProductCommand modifyProductCommand);
    ApiResponse<ProductResponseData> deleteTemplateProduct(@Valid DeleteProductCommand deleteProductCommand);
    // template attribute
    ApiResponse<TemplateAttributeResponseData> createTemplateAttribute(@Valid CreateTemplateAttributeCommand createTemplateAttributeCommand);
    ApiResponse<TemplateAttributeResponseData> getSingleTemplateAttribute(@Valid GetTemplateAttributeQuery getTemplateAttributeQuery);
    ApiResponse<TemplateAttributesListResponseData> getAllTemplateAttributes();
    ApiResponse<TemplateAttributeResponseData> updateTemplateAttribute(@Valid ModifyTemplateAttributeCommand modifyTemplateAttributeCommand);
    ApiResponse<TemplateAttributeResponseData> deleteTemplateAttribute(UUID templateAttributeId);
}
