package com.tuber.payment.service.domain.dto.message.broker;

import com.tuber.payment.service.domain.valueobject.enums.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PaymentRequest {
    private String id;
    private String sagaId;
    private UUID orderId;
    private UUID customerId;
    private BigDecimal price;
    private LocalDate createdAt;
    @Setter
    private PaymentOrderStatus paymentOrderStatus;
}
