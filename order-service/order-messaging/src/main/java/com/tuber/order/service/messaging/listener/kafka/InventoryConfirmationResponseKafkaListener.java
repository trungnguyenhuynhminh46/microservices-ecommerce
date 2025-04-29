package com.tuber.order.service.messaging.listener.kafka;

import com.tuber.domain.constant.response.code.ResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.kafka.order.avro.model.InventoryConfirmationResponseAvroModel;
import com.tuber.order.service.domain.dto.message.broker.InventoryConfirmationResponse;
import com.tuber.order.service.domain.exception.NotFoundOrderException;
import com.tuber.order.service.domain.ports.input.message.listener.inventory.InventoryConfirmationResponseListener;
import com.tuber.order.service.messaging.mapper.InventoryConfirmationMessageMapper;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import com.tuber.ordering.system.kafka.consumer.KafkaConsumer;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryConfirmationResponseKafkaListener implements KafkaConsumer<InventoryConfirmationResponseAvroModel> {
    InventoryConfirmationMessageMapper inventoryConfirmationMessageMapper;
    InventoryConfirmationResponseListener inventoryConfirmationResponseListener;

    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.inventory-confirmation-consumer-group-id}",
            topics = "${config-data.inventory-confirmation-response-topic-name}"
    )
    public void receive(
            @Payload List<InventoryConfirmationResponseAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        logReceivedMessages(messages, keys, partitions, offsets);
        processMessages(messages);
    }

    private void logReceivedMessages(List<InventoryConfirmationResponseAvroModel> messages,
                                     List<String> keys,
                                     List<Integer> partitions,
                                     List<Long> offsets) {
        log.info("Received {} messages with keys: {}, partitions: {}, offsets: {}",
                messages.size(), keys, partitions, offsets);
    }

    private void processMessages(List<InventoryConfirmationResponseAvroModel> messages) {
        messages.forEach(this::processSingleMessage);
    }

    private void processSingleMessage(InventoryConfirmationResponseAvroModel message) {
        try {
            handleInventoryConfirmationResponse(message);
        } catch (Exception exception) {
            handleProcessingException(message, exception);
        }
    }

    private void handleInventoryConfirmationResponse(InventoryConfirmationResponseAvroModel message) {
        InventoryConfirmationResponse inventoryConfirmationResponse = inventoryConfirmationMessageMapper.inventoryConfirmationResponseAvroModelToInventoryConfirmationResponse(message);
        switch (message.getOrderInventoryConfirmationStatus()) {
            case CONFIRMED -> {
                log.info("Finishing order processing after inventory confirmed for order with order id: {}",
                        message.getOrderId());
                inventoryConfirmationResponseListener.updateOrderAfterInventoryConfirmed(inventoryConfirmationResponse);
            }
            case FAILED -> {
                log.info("Cancelling order processing after inventory confirmation rejected for order with order id: {}",
                        message.getOrderId());
                inventoryConfirmationResponseListener.updateOrderAfterInventoryRejected(inventoryConfirmationResponse);
            }
        }
    }

    private void handleProcessingException(InventoryConfirmationResponseAvroModel message, Exception exception) {
        if (exception instanceof OptimisticLockingFailureException) {
            // Do not throw error for optimistic locking.
            log.error("Caught optimistic locking exception in PaymentResponseKafkaListener for order id: {}",
                    message.getOrderId());
            return;
        }

        if (exception instanceof NotFoundOrderException) {
            // Do nothing for NotFoundOrderException
            log.error("No order found for order id: {}", message.getOrderId());
            return;
        }

        throw new OrderDomainException(
                new ResponseCode(String.format("Failed to handle inventory confirmation response for order with order id: %s", message.getOrderId())),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception
        );
    }
}
