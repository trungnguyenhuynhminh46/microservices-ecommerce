package com.tuber.order.service.dataaccess.outbox.inventory.entity;

import com.tuber.domain.valueobject.enums.OrderStatus;
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
@Table(name = "inventory_confirmation_outbox")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryConfirmationOutboxMessageJpaEntity {
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
    OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    OutboxStatus outboxStatus;
    @Version
    int version;
}
