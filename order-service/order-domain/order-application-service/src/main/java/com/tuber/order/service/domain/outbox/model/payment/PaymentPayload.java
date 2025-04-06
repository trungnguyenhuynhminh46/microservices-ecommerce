package com.tuber.order.service.domain.outbox.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PaymentPayload {

    @JsonProperty
    private UUID orderId;
    @JsonProperty
    private UUID customerId;
    @JsonProperty
    private BigDecimal price;
    @JsonProperty
    private LocalDate createdAt;
    @JsonProperty
    private String paymentOrderStatus;
}
