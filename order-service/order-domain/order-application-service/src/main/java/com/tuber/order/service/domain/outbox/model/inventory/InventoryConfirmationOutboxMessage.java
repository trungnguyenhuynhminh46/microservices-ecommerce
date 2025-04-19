package com.tuber.order.service.domain.outbox.model.inventory;

import com.tuber.domain.valueobject.enums.OrderStatus;
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
public class InventoryConfirmationOutboxMessage {
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
    OrderStatus orderStatus;
    @Setter
    OutboxStatus outboxStatus;
    int version;
}
