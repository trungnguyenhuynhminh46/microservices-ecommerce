package com.tuber.inventory.service.domain.helper;

import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.entity.Inventory;
import com.tuber.domain.exception.InventoryDomainException;
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

    public Inventory saveInventory(Inventory inventory) {
        Inventory savedInventory = inventoryRepository.save(inventory);
        if (savedInventory == null) {
            log.error(String.format("Failed to save inventory with product id %s", inventory.getProduct().getProductId()));
            throw new InventoryDomainException(InventoryResponseCode.INVENTORY_SAVED_FAILED, HttpStatus.INTERNAL_SERVER_ERROR.value(), inventory.getProduct().getProductId());
        }
        return savedInventory;
    }

    //TODO: Implement this method
    public FulfillmentHistory saveFulfillmentHistory(FulfillmentHistory fulfillmentHistory) {
        return null;
    }
}
