package com.tuber.order.service.domain.ports.output.repository.outbox;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.saga.SagaStatus;

import java.util.Optional;
import java.util.UUID;

public interface InventoryConfirmationOutboxRepository {

    InventoryConfirmationOutboxMessage save(InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage);
    Optional<InventoryConfirmationOutboxMessage> findBySagaIdAndTypeAndSagaStatusIn(
            UUID sagaId,
            String type,
            SagaStatus... sagaStatus
    );
}
