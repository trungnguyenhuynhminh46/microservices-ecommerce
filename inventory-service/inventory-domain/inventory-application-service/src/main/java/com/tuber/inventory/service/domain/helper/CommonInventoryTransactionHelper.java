package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.constant.InventoryResponseCode;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.inventory.service.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryTransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommonInventoryTransactionHelper {
    InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryTransaction saveInventoryTransaction(InventoryTransaction inventoryTransaction) {
        InventoryTransaction savedInventoryTransaction = inventoryTransactionRepository.save(inventoryTransaction);
        if (savedInventoryTransaction == null) {
            log.error(String.format("Failed to save inventory transaction with id %s", inventoryTransaction.getId()));
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_TRANSACTION_SAVED_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), inventoryTransaction.getId().getValue());
        }
        return savedInventoryTransaction;
    }
}
