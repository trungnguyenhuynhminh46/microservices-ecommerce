package com.tuber.order.service.dataaccess.outbox.inventory.adapter;

import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.outbox.InventoryConfirmationOutboxRepository;
import com.tuber.saga.SagaStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

//TODO: Implement this class
@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationOutboxRepositoryImpl implements InventoryConfirmationOutboxRepository {
    @Override
    public InventoryConfirmationOutboxMessage save(InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage) {
        return null;
    }

    @Override
    public Optional<InventoryConfirmationOutboxMessage> findBySagaIdAndTypeAndSagaStatusIn(UUID sagaId, String type, SagaStatus... sagaStatus) {
        return Optional.empty();
    }
}
