package com.tuber.order.service.dataaccess.outbox.inventory.adapter;

import com.tuber.order.service.dataaccess.outbox.inventory.mapper.InventoryConfirmationOutboxDataAccessMapper;
import com.tuber.order.service.dataaccess.outbox.inventory.repository.InventoryConfirmationOutboxJpaRepository;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.outbox.InventoryConfirmationOutboxRepository;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationOutboxRepositoryImpl implements InventoryConfirmationOutboxRepository {
    InventoryConfirmationOutboxJpaRepository inventoryConfirmationOutboxJpaRepository;
    InventoryConfirmationOutboxDataAccessMapper inventoryConfirmationOutboxDataAccessMapper;

    @Override
    public InventoryConfirmationOutboxMessage save(InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage) {
        return inventoryConfirmationOutboxDataAccessMapper
                .inventoryConfirmationOutboxMessageJpaEntityToInventoryConfirmationOutboxMessage(
                        inventoryConfirmationOutboxJpaRepository
                                .save(inventoryConfirmationOutboxDataAccessMapper
                                        .inventoryConfirmationOutboxMessageToInventoryConfirmationOutboxMessageJpaEntity(
                                                inventoryConfirmationOutboxMessage)));

    }

    @Override
    public Optional<InventoryConfirmationOutboxMessage> findBySagaIdAndTypeAndSagaStatusIn(UUID sagaId, String type, SagaStatus... sagaStatus) {
        return inventoryConfirmationOutboxJpaRepository
                .findByTypeAndSagaIdAndSagaStatusIn(type, sagaId, List.of(sagaStatus))
                .map(inventoryConfirmationOutboxDataAccessMapper::inventoryConfirmationOutboxMessageJpaEntityToInventoryConfirmationOutboxMessage);
    }

    @Override
    public Optional<List<InventoryConfirmationOutboxMessage>> findByTypeAndOutboxStatusAndSagaStatusIn(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatuses) {
        return inventoryConfirmationOutboxJpaRepository
                .findByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus, List.of(sagaStatuses))
                .map(inventoryConfirmationOutboxJpaEntities -> inventoryConfirmationOutboxJpaEntities
                        .stream()
                        .map(inventoryConfirmationOutboxDataAccessMapper::inventoryConfirmationOutboxMessageJpaEntityToInventoryConfirmationOutboxMessage)
                        .toList());
    }

    @Override
    public void deleteByTypeAndOutboxStatusAndSagaStatusIn(String type, OutboxStatus outboxStatus, SagaStatus... sagaStatus) {
        inventoryConfirmationOutboxJpaRepository
                .deleteByTypeAndOutboxStatusAndSagaStatusIn(type, outboxStatus, List.of(sagaStatus));
    }
}
