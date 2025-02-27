package com.tuber.inventory.service.domain.ports.output.http.client;

import com.tuber.application.configuration.http.client.AuthenticationRequestConfiguration;
import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.http.client.product.ProductResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product-service.url}", configuration = {AuthenticationRequestConfiguration.class})
public interface ProductServiceClient {
    @GetMapping(value = "/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<ProductResponseData>> getProductDetail(@PathVariable String productId);
}
