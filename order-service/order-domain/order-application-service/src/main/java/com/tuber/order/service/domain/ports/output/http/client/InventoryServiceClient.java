package com.tuber.order.service.domain.ports.output.http.client;

import com.tuber.application.configuration.http.client.AuthenticationRequestConfiguration;
import com.tuber.application.handler.ApiResponse;
import com.tuber.order.service.domain.dto.http.client.inventory.GetInventoryDetailsQuery;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailsResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${inventory-service.url}", configuration = {AuthenticationRequestConfiguration.class})
public interface InventoryServiceClient {
    @PostMapping(value = "/inventory/internal/inventories/details", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<InternalInventoryDetailsResponseData>> getInventoryDetails(@RequestBody GetInventoryDetailsQuery getInventoryDetailsQuery);
}
