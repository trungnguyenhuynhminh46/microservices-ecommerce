package com.tuber.order.service.domain.dto.message.broker;

import com.tuber.domain.valueobject.enums.OrderInventoryConfirmationStatus;
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
public class InventoryConfirmationResponse {
    UUID id;
    UUID sagaId;
    UUID orderId;
    UUID fulfillHistoryId;
    LocalDate createdAt;
    OrderInventoryConfirmationStatus orderInventoryConfirmationStatus;
    List<String> failureMessages;
}
