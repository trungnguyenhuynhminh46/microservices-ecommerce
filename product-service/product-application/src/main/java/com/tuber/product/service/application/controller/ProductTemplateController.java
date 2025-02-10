package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.product.*;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/${service.name}/template/products", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class ProductTemplateController {
    private final ProductApplicationService productApplicationService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TEMPLATE')")
    public ResponseEntity<ApiResponse<ProductResponseData>> createTemplateProduct(@RequestBody CreateProductCommand createProductCommand) {
        ApiResponse<ProductResponseData> createProductResponse = productApplicationService.createTemplateProduct(createProductCommand);
        log.info("Successfully created product with name {}", createProductCommand.getName());
        return ResponseEntity.ok(createProductResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseData>> getSingleTemplate(@PathVariable UUID productId) {
        GetProductQuery getProductQuery = GetProductQuery.builder().id(productId).build();
        ApiResponse<ProductResponseData> productResponse = productApplicationService.getSingleTemplateProduct(getProductQuery);
        log.info("Successfully fetched product with id {}", productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProductsListResponseData>> getAllTemplate() {
        ApiResponse<ProductsListResponseData> productsResponse = productApplicationService.getAllTemplateProducts();
        log.info("Successfully fetched all products");
        return ResponseEntity.ok(productsResponse);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAuthority('UPDATE_TEMPLATE')")
    public ResponseEntity<ApiResponse<ProductResponseData>> updateTemplate(@PathVariable("productId") UUID productId, @RequestBody ModifyProductCommand modifyProductCommand) {
        modifyProductCommand.setId(productId);
        ApiResponse<ProductResponseData> updateProductResponse = productApplicationService.updateTemplateProduct(modifyProductCommand);
        log.info("Successfully updated product with id {}", productId);
        return ResponseEntity.ok(updateProductResponse);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('DELETE_TEMPLATE')")
    public ResponseEntity<ApiResponse<ProductResponseData>> deleteTemplate(@PathVariable("productId") UUID productId) {
        DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder().id(productId).build();
        ApiResponse<ProductResponseData> deleteProductResponse = productApplicationService.deleteTemplateProduct(deleteProductCommand);
        log.info("Successfully deleted product with id {}", productId);
        return ResponseEntity.ok(deleteProductResponse);
    }
}
