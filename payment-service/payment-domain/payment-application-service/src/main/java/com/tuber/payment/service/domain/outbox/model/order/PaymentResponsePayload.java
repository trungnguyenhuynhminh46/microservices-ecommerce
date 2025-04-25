package com.tuber.payment.service.domain.outbox.model.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentResponsePayload {
    @JsonProperty
    UUID paymentId;
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
    String paymentStatus;
    @JsonProperty
    List<String> failureMessages;
}
