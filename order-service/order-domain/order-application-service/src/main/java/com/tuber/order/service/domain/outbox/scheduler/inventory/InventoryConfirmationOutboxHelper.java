package com.tuber.order.service.domain.outbox.scheduler.inventory;

import com.tuber.application.helper.CommonHelper;
import com.tuber.domain.constant.response.code.OrderResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.domain.valueobject.enums.OrderStatus;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationEventPayload;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.outbox.InventoryConfirmationOutboxRepository;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.order.SagaName;
import com.tuber.saga.SagaStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryConfirmationOutboxHelper {
    InventoryConfirmationOutboxRepository inventoryConfirmationOutboxRepository;
    CommonHelper commonHelper;

    private InventoryConfirmationOutboxMessage createOutboxMessage(
            InventoryConfirmationEventPayload inventoryConfirmationEventPayload,
            OrderStatus orderStatus,
            SagaStatus sagaStatus,
            OutboxStatus outboxStatus,
            UUID sagaId
    ) throws OrderDomainException {
        OrderDomainException throwable = new OrderDomainException(
                new OrderResponseCode(
                        String.format("Failed to create InventoryConfirmationEventPayload object for order id: %s, inventory id: %s",
                                inventoryConfirmationEventPayload.getOrderId(),
                                inventoryConfirmationEventPayload.getInventoryId())),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return InventoryConfirmationOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(inventoryConfirmationEventPayload.getCreatedAt())
                .type(SagaName.ORDER_PROCESSING_SAGA.name())
                .payload(commonHelper.mapObjectIntoString(
                        inventoryConfirmationEventPayload,
                        throwable
                ))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(outboxStatus)
                .build();
    }

    @Transactional
    public void saveInventoryConfirmationOutboxMessage(
            InventoryConfirmationEventPayload inventoryConfirmationEventPayload,
            UUID sagaId,
            SagaStatus sagaStatus,
            OutboxStatus outboxStatus,
            OrderStatus orderStatus
    ) {
        InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage = createOutboxMessage(
                inventoryConfirmationEventPayload,
                orderStatus,
                sagaStatus,
                outboxStatus,
                sagaId
        );

        InventoryConfirmationOutboxMessage savedMessage = inventoryConfirmationOutboxRepository.save(inventoryConfirmationOutboxMessage);
        if (savedMessage == null) {
            log.error("Could not save outbox inventory confirmation  message, order with orderId: {}, inventoryId: {}",
                    inventoryConfirmationEventPayload.getOrderId(),
                    inventoryConfirmationEventPayload.getInventoryId()
            );
            throw new OrderDomainException(
                    OrderResponseCode.FAILED_TO_SAVE_INVENTORY_OUTBOX,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    inventoryConfirmationEventPayload.getOrderId(),
                    inventoryConfirmationEventPayload.getInventoryId()
            );
        }
    }

    public InventoryConfirmationOutboxMessage verifyInventoryConfirmationOutboxMessageExists(
            UUID sagaId,
            SagaStatus... sagaStatus
    ) {
        Optional<InventoryConfirmationOutboxMessage> response =
                inventoryConfirmationOutboxRepository.findBySagaIdAndTypeAndSagaStatusIn(
                        sagaId,
                        SagaName.ORDER_PROCESSING_SAGA.name(),
                        sagaStatus
                );
        if (response.isEmpty()) {
            log.error("Inventory confirmation outbox message with saga id: {} could not be found!", sagaId);
            throw new OrderDomainException(
                    OrderResponseCode.INVENTORY_CONFIRMATION_OUTBOX_MESSAGE_NOT_FOUND,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    sagaId
            );
        }
        return response.get();
    }

    public void updateInventoryConfirmationOutboxMessage(
            InventoryConfirmationOutboxMessage message,
            OrderStatus orderStatus,
            SagaStatus sagaStatus
    ) {
        message.setProcessedAt(LocalDate.now());
        message.setOrderStatus(orderStatus);
        message.setSagaStatus(sagaStatus);
        inventoryConfirmationOutboxRepository.save(message);
    }
}
