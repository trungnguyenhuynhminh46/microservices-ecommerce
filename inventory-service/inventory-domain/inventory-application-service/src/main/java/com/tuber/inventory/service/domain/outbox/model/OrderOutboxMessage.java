package com.tuber.inventory.service.domain.outbox.model;

import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderOutboxMessage {
    UUID id;
    UUID sagaId;
    LocalDate createdAt;
    @Setter
    LocalDate processedAt;
    String type;
    String payload;
    @Setter
    SagaStatus sagaStatus;
    @Setter
    OutboxStatus outboxStatus;
    @Setter
    OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
    int version;
}
