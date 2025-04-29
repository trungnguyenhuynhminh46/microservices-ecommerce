package com.tuber.order.service.domain.ports.input.message.listener.inventory;

import com.tuber.order.service.domain.dto.message.broker.InventoryConfirmationResponse;
import com.tuber.order.service.domain.saga.InventoryConfirmationSaga;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationResponseListenerImpl implements InventoryConfirmationResponseListener {
    InventoryConfirmationSaga inventoryConfirmationSaga;
    @Override
    public void updateOrderAfterInventoryConfirmed(InventoryConfirmationResponse inventoryConfirmationResponse) {
        inventoryConfirmationSaga.process(inventoryConfirmationResponse);
        log.info("Order Inventory Confirmation Saga process is completed for order with order id: {}",
                inventoryConfirmationResponse.getOrderId());
    }

    @Override
    public void updateOrderAfterInventoryRejected(InventoryConfirmationResponse inventoryConfirmationResponse) {
        inventoryConfirmationSaga.rollback(inventoryConfirmationResponse);
        log.info("Order Inventory Confirmation Saga rollback is completed for order with order id: {}, the failure messages: {}",
                inventoryConfirmationResponse.getOrderId(),
                String.join(",", inventoryConfirmationResponse.getFailureMessages()));
    }
}
