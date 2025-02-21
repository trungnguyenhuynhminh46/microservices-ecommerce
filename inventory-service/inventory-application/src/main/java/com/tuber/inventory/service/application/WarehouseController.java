package com.tuber.inventory.service.application;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.ports.input.service.InventoryApplicationService;
import jakarta.validation.Valid;
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
@RequestMapping(value = "/${service.name}/warehouses", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class WarehouseController {
    private final InventoryApplicationService inventoryApplicationService;
    @PostMapping
    public ResponseEntity<ApiResponse<Object>> createWarehouse(@RequestBody @Valid Object createWarehouseCommand) {
        return null;
    }
}
