package com.tuber.inventory.service.domain.helper;

import com.tuber.inventory.service.domain.InventoryDomainService;
import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.entity.FulfillmentHistory;
import com.tuber.inventory.service.domain.mapper.FulfillmentHistoryMapper;
import com.tuber.inventory.service.domain.outbox.scheduler.OrderOutboxHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    //TODO: Implement this method
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
        inventoryDomainService.validateAndInitializeFulfillmentHistory(history);
        commonInventoryHelper.saveFulfillmentHistory(history);
        //TODO: Persist order outbox message to send inventory confirmation response to message broker
    }

    //TODO: Implement this method
    protected boolean republishIfOutboxMessageExisted(UUID sagaID) {
        return false;
    }
}
