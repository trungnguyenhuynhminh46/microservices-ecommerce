package com.tuber.inventory.service.domain.ports.input.message.listerner;

import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;

public interface InventoryConfirmationRequestListener {
    void confirmGoodsAvailable(InventoryConfirmationRequest inventoryConfirmationRequest);
}
