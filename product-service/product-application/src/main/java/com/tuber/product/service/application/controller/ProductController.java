package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.validators.ValidUUID;
import com.tuber.product.service.domain.dto.product.*;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/products", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class ProductController {
    private final ProductApplicationService productApplicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseData>> createProduct(@RequestBody CreateProductCommand createProductCommand) {
        ApiResponse<ProductResponseData> createProductResponse = productApplicationService.createProduct(createProductCommand);
        log.info("Successfully created product with name {}", createProductCommand.getName());
        return ResponseEntity.ok(createProductResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseData>> getSingle(@PathVariable @ValidUUID String productId) {
        GetProductQuery getProductQuery = GetProductQuery.builder().id(UUID.fromString(productId)).build();
        ApiResponse<ProductResponseData> productResponse = productApplicationService.getSingleProduct(getProductQuery);
        log.info("Successfully fetched product with id {}", productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProductsListResponseData>> getAll() {
        ApiResponse<ProductsListResponseData> productsResponse = productApplicationService.getAllProducts();
        log.info("Successfully fetched all products");
        return ResponseEntity.ok(productsResponse);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseData>> update(@PathVariable("productId") @ValidUUID String productId, @RequestBody ModifyProductCommand modifyProductCommand) {
        modifyProductCommand.setId(UUID.fromString(productId));
        ApiResponse<ProductResponseData> updateProductResponse = productApplicationService.updateProduct(modifyProductCommand);
        log.info("Successfully updated product with id {}", productId);
        return ResponseEntity.ok(updateProductResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponseData>> delete(@PathVariable("productId") @ValidUUID String productId) {
        DeleteProductCommand deleteProductCommand = DeleteProductCommand.builder().id(UUID.fromString(productId)).build();
        ApiResponse<ProductResponseData> deleteProductResponse = productApplicationService.deleteProduct(deleteProductCommand);
        log.info("Successfully deleted product with id {}", productId);
        return ResponseEntity.ok(deleteProductResponse);
    }
}
