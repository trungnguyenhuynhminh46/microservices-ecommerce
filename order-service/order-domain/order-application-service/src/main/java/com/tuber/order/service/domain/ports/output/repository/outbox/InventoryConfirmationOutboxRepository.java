package com.tuber.order.service.domain.ports.output.repository.outbox;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryConfirmationOutboxRepository {

    InventoryConfirmationOutboxMessage save(InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage);

    Optional<InventoryConfirmationOutboxMessage> findBySagaIdAndTypeAndSagaStatusIn(
            UUID sagaId,
            String type,
            SagaStatus... sagaStatus
    );

    Optional<List<InventoryConfirmationOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatuses
    );

    void deleteByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            SagaStatus... sagaStatus
    );
}
