package com.tuber.order.service.domain.outbox.model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentPayload {
    @JsonProperty
    UUID orderId;
    @JsonProperty
    UUID customerId;
    @JsonProperty
    BigDecimal totalPrice;
    @JsonProperty
    BigDecimal finalPrice;
    @JsonProperty
    LocalDate createdAt;
    @JsonProperty
    String paymentOrderStatus;
}
