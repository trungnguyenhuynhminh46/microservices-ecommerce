package com.tuber.order.service.domain.dto.message.broker;

import com.tuber.domain.valueobject.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponse {
    UUID id;
    UUID sagaId;
    UUID paymentId;
    UUID orderId;
    UUID customerId;
    BigDecimal price;
    LocalDate createdAt;
    PaymentStatus paymentStatus;
    List<String> failureMessages;
}
