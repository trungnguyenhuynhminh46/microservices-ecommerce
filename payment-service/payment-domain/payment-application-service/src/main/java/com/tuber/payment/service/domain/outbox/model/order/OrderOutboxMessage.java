package com.tuber.payment.service.domain.outbox.model.order;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
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
    PaymentStatus paymentStatus;
    @Setter
    OutboxStatus outboxStatus;
    int version;
}
