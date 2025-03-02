package com.tuber.inventory.service.domain.helper.transaction;

import com.tuber.application.handler.ApiResponse;
import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.dto.transaction.TransactionsListResponseData;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.inventory.service.domain.mapper.TransactionMapper;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionsHelper {
    TransactionMapper transactionMapper;
    InventoryTransactionRepository inventoryTransactionRepository;
    public ApiResponse<TransactionsListResponseData> getAllTransactions() {
        List<InventoryTransaction> inventoryTransactionList = inventoryTransactionRepository.findAll();
        return ApiResponse.<TransactionsListResponseData>builder()
                .code(InventoryResponseCode.SUCCESS_RESPONSE.getCode())
                .message("Transactions retrieved successfully")
                .data(transactionMapper.transactionsListToTransactionsListResponseData(inventoryTransactionList))
                .build();
    }
}
