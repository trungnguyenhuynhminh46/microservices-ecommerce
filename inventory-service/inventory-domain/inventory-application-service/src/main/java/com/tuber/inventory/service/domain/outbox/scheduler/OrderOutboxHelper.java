package com.tuber.inventory.service.domain.outbox.scheduler;

import com.tuber.application.helper.CommonHelper;
import com.tuber.domain.constant.response.code.InventoryResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.outbox.model.OrderEventPayload;
import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.ports.output.repository.outbox.OrderOutboxRepository;
import com.tuber.inventory.service.domain.valueobject.enums.OrderInventoryConfirmationStatus;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.order.SagaName;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OrderOutboxHelper {
    OrderOutboxRepository orderOutboxRepository;
    CommonHelper commonHelper;

    public Optional<OrderOutboxMessage> findOrderOutboxMessageWithOutboxStatus(
            UUID sagaId,
            OutboxStatus outboxStatus
    ) {
        return orderOutboxRepository.findBySagaIdAndTypeAndOutboxStatus(
                sagaId,
                SagaName.ORDER_PROCESSING_SAGA.name(),
                outboxStatus
        );
    }

    public OrderOutboxMessage createOrderOutboxMessage(
            OrderEventPayload payload,
            OrderInventoryConfirmationStatus orderInventoryConfirmationStatus,
            OutboxStatus outboxStatus,
            UUID sagaId) throws InventoryDomainException {
        InventoryDomainException throwable = new InventoryDomainException(
                new InventoryResponseCode(
                        String.format("Failed to create OrderOutboxMessage object for order id: %s",
                                payload.getOrderId()
                        )
                ),
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return OrderOutboxMessage.builder()
                .id(UUID.randomUUID())
                .sagaId(sagaId)
                .createdAt(payload.getCreatedAt())
                .type(SagaName.ORDER_PROCESSING_SAGA.name())
                .payload(commonHelper.mapObjectIntoString(payload, throwable))
                .orderInventoryConfirmationStatus(orderInventoryConfirmationStatus)
                .outboxStatus(outboxStatus)
                .build();
    }


    @Transactional
    public OrderOutboxMessage saveOrderOutboxMessage(OrderOutboxMessage orderOutboxMessage) {
        OrderOutboxMessage response = orderOutboxRepository.save(orderOutboxMessage);
        if (response == null) {
            log.error("Failed to save order outbox message with saga id: {}", orderOutboxMessage.getSagaId());
            throw new InventoryDomainException(InventoryResponseCode.CAN_NOT_SAVE_ORDER_OUTBOX_MESSAGE,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    orderOutboxMessage.getSagaId()
            );
        }
        return response;
    }

    @Transactional
    public void updateOutboxStatus(
            OrderOutboxMessage message,
            OutboxStatus outboxStatus
    ) {
        message.setOutboxStatus(outboxStatus);
        this.saveOrderOutboxMessage(message);
        log.info("Order outbox message with saga id: {} has been updated to outbox status: {}",
                message.getSagaId(),
                outboxStatus
        );
    }
}
