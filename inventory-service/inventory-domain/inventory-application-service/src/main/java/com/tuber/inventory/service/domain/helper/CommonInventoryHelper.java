package com.tuber.inventory.service.domain.helper;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.ports.output.repository.FulfillmentHistoryRepository;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryRepository;
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
public class CommonInventoryHelper {
    InventoryRepository inventoryRepository;
    FulfillmentHistoryRepository fulfillmentHistoryRepository;

    public Inventory saveInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        if (savedInventory == null) {
            log.error(String.format("Failed to save inventory with product id %s", inventory.getProduct().getProductId()));
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_SAVED_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), inventory.getProduct().getProductId());
        }
        return savedInventory;
    }

    public FulfillmentHistory saveFulfillmentHistory(FulfillmentHistory fulfillmentHistory) {
        FulfillmentHistory savedFulfillmentHistory = fulfillmentHistoryRepository.save(fulfillmentHistory);
        if (savedFulfillmentHistory == null) {
            log.error(String.format("Failed to save fulfillment history with id %s", fulfillmentHistory.getId()));
        }
        return savedFulfillmentHistory;
    }
}
