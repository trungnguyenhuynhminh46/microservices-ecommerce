package com.tuber.order.service.domain.ports.input.message.listener.inventory;

import com.tuber.order.service.domain.dto.message.broker.InventoryConfirmationResponse;

public interface InventoryConfirmationResponseListener {
    void updateOrderAfterInventoryConfirmed(InventoryConfirmationResponse inventoryConfirmationResponse);
    void updateOrderAfterInventoryRejected(InventoryConfirmationResponse inventoryConfirmationResponse);
}
