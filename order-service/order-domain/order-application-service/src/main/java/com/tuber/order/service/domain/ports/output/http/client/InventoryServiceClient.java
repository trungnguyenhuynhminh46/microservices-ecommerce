package com.tuber.order.service.domain.ports.output.http.client;

import com.tuber.application.configuration.http.client.AuthenticationRequestConfiguration;
import com.tuber.application.handler.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service", url = "${inventory-service.url}", configuration = {AuthenticationRequestConfiguration.class})
public interface InventoryServiceClient {
    @PostMapping(value = "/inventories/internal/details", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<Object>> getInventoryDetails(Object request);
}
