package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.InventoryDomainService;
import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.event.InventoryConfirmationEvent;
import com.tuber.inventory.service.domain.mapper.FulfillmentHistoryMapper;
import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.outbox.scheduler.OrderOutboxHelper;
import com.tuber.inventory.service.domain.ports.output.message.publisher.InventoryConfirmationResponsePublisher;
import com.tuber.outbox.OutboxStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationMessageHelper {
    InventoryDomainService inventoryDomainService;
    FulfillmentHistoryMapper fulfillmentHistoryMapper;
    CommonInventoryHelper commonInventoryHelper;
    OrderOutboxHelper orderOutboxHelper;
    InventoryConfirmationResponsePublisher inventoryConfirmationResponsePublisher;

    @Transactional
    public void persistFulfillmentHistory(InventoryConfirmationRequest inventoryConfirmationRequest) {
        if (republishIfOutboxMessageExisted(inventoryConfirmationRequest.getSagaId())) {
            log.info("The outbox message with saga id: {} exists!",
                    inventoryConfirmationRequest.getSagaId());
            return;
        }
        log.info("Processing outbox message with saga id: {}",
                inventoryConfirmationRequest.getSagaId());

        FulfillmentHistory history = fulfillmentHistoryMapper.inventoryConfirmationRequestToFulfillmentHistory(
                inventoryConfirmationRequest
        );
        InventoryConfirmationEvent inventoryConfirmationEvent = inventoryDomainService.validateAndInitializeFulfillmentHistory(history);
        commonInventoryHelper.saveFulfillmentHistory(history);
        orderOutboxHelper.saveOrderOutboxMessage(
                orderOutboxHelper.createOrderOutboxMessage(
                        fulfillmentHistoryMapper.inventoryConfirmationEventToOrderEventPayload(
                                inventoryConfirmationEvent
                        ),
                        inventoryConfirmationEvent.getFulfillmentHistory().getOrderInventoryConfirmationStatus(),
                        OutboxStatus.STARTED,
                        inventoryConfirmationRequest.getSagaId()
                )
        );
    }

    protected boolean republishIfOutboxMessageExisted(UUID sagaID) {
        Optional<OrderOutboxMessage> outboxMessage =
                orderOutboxHelper.findOrderOutboxMessageWithOutboxStatus(sagaID, OutboxStatus.COMPLETED);
        if (outboxMessage.isPresent()) {
            inventoryConfirmationResponsePublisher.publish(outboxMessage.get(), orderOutboxHelper::updateOutboxStatus);
            return true;
        }
        return false;
    }
}
