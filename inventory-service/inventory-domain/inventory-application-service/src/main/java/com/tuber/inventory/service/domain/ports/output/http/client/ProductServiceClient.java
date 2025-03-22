package com.tuber.inventory.service.domain.ports.output.http.client;

import com.tuber.application.configuration.http.client.AuthenticationRequestConfiguration;
import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.http.client.product.GetProductDetailsQuery;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductResponseData;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductsListResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "product-service", url = "${product-service.url}", configuration = {AuthenticationRequestConfiguration.class})
public interface ProductServiceClient {
    @GetMapping(value = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<ProductResponseData>> getProductDetail(@PathVariable String productId);

    @PostMapping(value = "/products/internal/products/details", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<ProductsListResponseData>> getProductsDetailsByIds(GetProductDetailsQuery getProductDetailsQuery);
}
