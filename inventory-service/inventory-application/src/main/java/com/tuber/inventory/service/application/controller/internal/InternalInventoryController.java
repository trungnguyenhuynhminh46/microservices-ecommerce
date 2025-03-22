package com.tuber.inventory.service.application.controller.internal;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.inventory.internal.GetInventoryDetailsQuery;
import com.tuber.inventory.service.domain.dto.inventory.internal.InternalInventoryDetailsResponseData;
import com.tuber.inventory.service.domain.ports.input.service.InventoryApplicationService;
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
@RequestMapping(value = "/${service.name}/internal/inventories", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class InternalInventoryController {
    private final InventoryApplicationService inventoryApplicationService;

    @PostMapping("/details")
    public ResponseEntity<ApiResponse<InternalInventoryDetailsResponseData>> getInventoryDetails(@RequestBody GetInventoryDetailsQuery getInventoryDetailsQuery) {
        log.info("Fetching inventory details");
        ApiResponse<InternalInventoryDetailsResponseData> inventoryDetails = inventoryApplicationService.getInventoryDetails(getInventoryDetailsQuery);
        log.info("Successfully fetched inventory details");
        return ResponseEntity.ok(inventoryDetails);
    }
}
