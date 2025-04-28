package com.tuber.inventory.service.dataaccess.outbox.order.entity;

import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_outbox")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderOutboxMessageJpaEntity {
    @Id
    UUID id;
    UUID sagaId;
    LocalDate createdAt;
    LocalDate processedAt;
    String type;
    String payload;
    @Enumerated(EnumType.STRING)
    SagaStatus sagaStatus;
    @Enumerated(EnumType.STRING)
    OutboxStatus outboxStatus;
    @Enumerated(EnumType.STRING)
    OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
    @Version
    int version;
}
