package com.tuber.inventory.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.dto.transaction.TransactionsListResponseData;
import com.tuber.inventory.service.domain.ports.input.service.InventoryApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/${service.name}/transactions", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class TransactionController {
    private final InventoryApplicationService inventoryApplicationService;

    @GetMapping
    @PreAuthorize("hasAuthority('TRANSFER_GOODS')")
    public ResponseEntity<ApiResponse<TransactionsListResponseData>> getTransactions() {
        ApiResponse<TransactionsListResponseData> transactionsListResponseData = inventoryApplicationService.getAllTransactions();
        log.info("Successfully fetched all transactions");
        return ResponseEntity.ok(transactionsListResponseData);
    }
}
