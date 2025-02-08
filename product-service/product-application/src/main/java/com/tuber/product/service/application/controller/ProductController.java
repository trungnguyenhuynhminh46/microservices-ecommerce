package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.product.CreateProductCommand;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
import com.tuber.product.service.domain.dto.product.ProductsListResponseData;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
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
    public ResponseEntity<ApiResponse<ProductResponseData>> getSingleProduct(@PathVariable UUID productId) {
        ApiResponse<ProductResponseData> productResponse = productApplicationService.getSingleProduct(productId);
        log.info("Successfully fetched product with id {}", productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<ProductsListResponseData>> getAllProduct() {
        ApiResponse<ProductsListResponseData> productsResponse = productApplicationService.getAllProducts();
        log.info("Successfully fetched all products");
        return ResponseEntity.ok(productsResponse);
    }
}
