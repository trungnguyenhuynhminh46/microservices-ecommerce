package com.tuber.product.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.validators.ValidUUID;
import com.tuber.product.service.domain.dto.template.product.*;
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
public class TemplateProductController {
    private final ProductApplicationService productApplicationService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_TEMPLATE')")
    public ResponseEntity<ApiResponse<TemplateProductResponseData>> createTemplateProduct(@RequestBody CreateTemplateProductCommand createTemplateProductCommand) {
        ApiResponse<TemplateProductResponseData> createProductResponse = productApplicationService.createTemplateProduct(createTemplateProductCommand);
        log.info("Successfully created product with name {}", createTemplateProductCommand.getName());
        return ResponseEntity.ok(createProductResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<TemplateProductResponseData>> getSingleTemplate(@PathVariable @ValidUUID String productId) {
        GetTemplateProductQuery getTemplateProductQuery = GetTemplateProductQuery.builder().id(UUID.fromString(productId)).build();
        ApiResponse<TemplateProductResponseData> productResponse = productApplicationService.getSingleTemplateProduct(getTemplateProductQuery);
        log.info("Successfully fetched product with id {}", productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<TemplateProductsListResponseData>> getAllTemplate() {
        ApiResponse<TemplateProductsListResponseData> productsResponse = productApplicationService.getAllTemplateProducts();
        log.info("Successfully fetched all products");
        return ResponseEntity.ok(productsResponse);
    }

    @PatchMapping("/{productId}")
    @PreAuthorize("hasAuthority('UPDATE_TEMPLATE')")
    public ResponseEntity<ApiResponse<TemplateProductResponseData>> updateTemplate(@PathVariable("productId") @ValidUUID String productId, @RequestBody ModifyTemplateProductCommand modifyTemplateProductCommand) {
        modifyTemplateProductCommand.setId(UUID.fromString(productId));
        ApiResponse<TemplateProductResponseData> updateProductResponse = productApplicationService.updateTemplateProduct(modifyTemplateProductCommand);
        log.info("Successfully updated product with id {}", productId);
        return ResponseEntity.ok(updateProductResponse);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasAuthority('DELETE_TEMPLATE')")
    public ResponseEntity<ApiResponse<TemplateProductResponseData>> deleteTemplate(@PathVariable("productId") @ValidUUID String productId) {
        DeleteTemplateProductCommand deleteTemplateProductCommand = DeleteTemplateProductCommand.builder().id(UUID.fromString(productId)).build();
        ApiResponse<TemplateProductResponseData> deleteProductResponse = productApplicationService.deleteTemplateProduct(deleteTemplateProductCommand);
        log.info("Successfully deleted product with id {}", productId);
        return ResponseEntity.ok(deleteProductResponse);
    }
}
