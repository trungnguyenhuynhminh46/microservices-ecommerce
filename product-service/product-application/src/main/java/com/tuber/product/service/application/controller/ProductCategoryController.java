package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.category.*;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/categories", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductApplicationService productApplicationService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_PRODUCT_CATEGORY')")
    public ResponseEntity<ApiResponse<ProductCategoryResponseData>> createCategory(@RequestBody CreateProductCategoryCommand createProductCategoryCommand) {
        ApiResponse<ProductCategoryResponseData> createProductCategoryResponse = productApplicationService.createProductCategory(createProductCategoryCommand);
        log.info("Successfully created product category with name {}", createProductCategoryCommand.getName());
        return ResponseEntity.ok(createProductCategoryResponse);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAuthority('GET_PRODUCT_CATEGORY')")
    public ResponseEntity<ApiResponse<ProductCategoryResponseData>> getSingle(@PathVariable("code") String code) {
        GetProductCategoryQuery getProductCategoryQuery = GetProductCategoryQuery.builder().code(code).build();
        ApiResponse<ProductCategoryResponseData> getProductCategoryResponse = productApplicationService.getProductCategory(getProductCategoryQuery);
        log.info("Returning product category with code: {}", code);
        return ResponseEntity.ok(getProductCategoryResponse);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('GET_PRODUCT_CATEGORY')")
    public ResponseEntity<ApiResponse<ProductCategoryListResponseData>> getAll() {
        ApiResponse<ProductCategoryListResponseData> getProductCategoriesResponse = productApplicationService.getAllProductCategories();
        log.info("Returning all product categories");
        return ResponseEntity.ok(getProductCategoriesResponse);
    }

    @PatchMapping("/{code}")
    @PreAuthorize("hasAuthority('UPDATE_PRODUCT_CATEGORY')")
    public ResponseEntity<ApiResponse<ProductCategoryResponseData>> update(@PathVariable("code") String code, @RequestBody ModifyProductCategoryCommand modifyProductCategoryCommand) {
        modifyProductCategoryCommand.setCode(code);
        ApiResponse<ProductCategoryResponseData> updateProductCategoryResponse = productApplicationService.updateProductCategory(modifyProductCategoryCommand);
        log.info("Successfully updated product category with code {}", code);
        return ResponseEntity.ok(updateProductCategoryResponse);
    }

    @DeleteMapping("/{code}")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT_CATEGORY')")
    public ResponseEntity<ApiResponse<ProductCategoryResponseData>> delete(@PathVariable("code") String code) {
        DeleteProductCategoryCommand deleteProductCategoryCommand = DeleteProductCategoryCommand.builder().code(code).build();
        ApiResponse<ProductCategoryResponseData> deleteProductCategoryResponse = productApplicationService.deleteProductCategory(deleteProductCategoryCommand);
        log.info("Successfully deleted product category with code {}", code);
        return ResponseEntity.ok(deleteProductCategoryResponse);
    }
}
