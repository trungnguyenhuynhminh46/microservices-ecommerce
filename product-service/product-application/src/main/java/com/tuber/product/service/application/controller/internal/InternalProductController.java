package com.tuber.product.service.application.controller.internal;

import com.tuber.application.dto.product.GetProductDetailsQuery;
import com.tuber.application.dto.product.ProductsListResponseData;
import com.tuber.application.handler.ApiResponse;
import com.tuber.product.service.domain.ports.input.service.ProductApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/internal/products", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class InternalProductController {
    private final ProductApplicationService productApplicationService;

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<ProductsListResponseData>> getProductDetails(@RequestBody GetProductDetailsQuery getProductDetailsQuery) {
        log.info("Fetching product details");
        ApiResponse<ProductsListResponseData> productDetails = productApplicationService.getProductDetails(getProductDetailsQuery);
        log.info("Successfully fetched product details");
        return ResponseEntity.ok(productDetails);
    }
}
