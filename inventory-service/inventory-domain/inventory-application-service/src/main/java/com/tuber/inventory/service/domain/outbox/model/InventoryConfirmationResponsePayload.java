package com.tuber.inventory.service.domain.outbox.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryConfirmationResponsePayload {
    @JsonProperty
    UUID orderId;
    @JsonProperty
    UUID fulfillHistoryId;
    @JsonProperty
    LocalDate createdAt;
    @JsonProperty
    String orderInventoryConfirmationStatus;
    @JsonProperty
    List<String> failureMessages;
}
