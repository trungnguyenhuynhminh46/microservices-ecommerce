package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.dto.product.CreateProductCommand;
import com.tuber.product.service.domain.dto.product.ProductResponseData;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
