package com.tuber.payment.service.dataaccess.outbox.order.entity;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.valueobject.enums.PaymentStatus;
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
public class OutboxOrderMessageJpaEntity {
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
    PaymentStatus paymentStatus;
    @Enumerated(EnumType.STRING)
    OutboxStatus outboxStatus;
    @Version
    int version;
}
