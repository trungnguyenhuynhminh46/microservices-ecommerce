package com.tuber.payment.service.domain.dto.message.broker;

import com.tuber.payment.service.domain.valueobject.enums.PaymentOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Builder
@AllArgsConstructor
public class PaymentRequest {
    private String id;
    private String sagaId;
    private String orderId;
    private String customerId;
    private BigDecimal price;
    private Instant createdAt;
    @Setter
    private PaymentOrderStatus paymentOrderStatus;

}
