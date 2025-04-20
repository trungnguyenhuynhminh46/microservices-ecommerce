package com.tuber.order.service.dataaccess.outbox.inventory.repository;

import com.tuber.order.service.dataaccess.outbox.inventory.entity.InventoryConfirmationOutboxMessageJpaEntity;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryConfirmationOutboxJpaRepository extends JpaRepository<InventoryConfirmationOutboxMessageJpaEntity, UUID> {
    Optional<List<InventoryConfirmationOutboxMessageJpaEntity>> findByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            List<SagaStatus> sagaStatus
    );
    Optional<InventoryConfirmationOutboxMessageJpaEntity> findByTypeAndSagaIdAndSagaStatusIn(
            String type,
            UUID sagaId,
            List<SagaStatus> sagaStatus
    );
    void deleteByTypeAndOutboxStatusAndSagaStatusIn(
            String type,
            OutboxStatus outboxStatus,
            List<SagaStatus> sagaStatus
    );
}
