package com.tuber.payment.service.domain.dto.message.broker;

import com.tuber.payment.service.domain.valueobject.enums.PaymentOrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRequest {
    UUID id;
    UUID sagaId;
    UUID orderId;
    UUID customerId;
    BigDecimal totalPrice;
    BigDecimal finalPrice;
    LocalDate createdAt;
    @Setter
    PaymentOrderStatus paymentOrderStatus;
}
