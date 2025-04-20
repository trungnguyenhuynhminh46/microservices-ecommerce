package com.tuber.inventory.service.messaging.listener.kafka;

import com.tuber.domain.constant.response.code.ResponseCode;
import com.tuber.domain.exception.InventoryDomainException;
import com.tuber.inventory.service.domain.dto.message.broker.InventoryConfirmationRequest;
import com.tuber.inventory.service.domain.exception.NotFoundInventoryException;
import com.tuber.inventory.service.messaging.mapper.InventoryMessageMapper;
import com.tuber.kafka.order.avro.model.InventoryConfirmationRequestAvroModel;
import com.tuber.ordering.system.kafka.consumer.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InventoryConfirmationRequestKafkaListener implements KafkaConsumer<InventoryConfirmationRequestAvroModel> {
    private static final String MYSQL_UNIQUE_CONSTRAINT_VIOLATION = "23000";
    InventoryMessageMapper inventoryMessageMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.inventory-confirmation-consumer-group-id}",
            topics = "${config-data.inventory-confirmation-request-topic-name}")
    public void receive(@Payload List<InventoryConfirmationRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        logReceivedMessages(messages, keys, partitions, offsets);
        processMessages(messages);
    }

    private void logReceivedMessages(List<InventoryConfirmationRequestAvroModel> messages,
                                     List<String> keys,
                                     List<Integer> partitions,
                                     List<Long> offsets) {
        log.info("Received {} messages with keys: {}, partitions: {}, offsets: {}",
                messages.size(), keys, partitions, offsets);
    }

    private void processMessages(List<InventoryConfirmationRequestAvroModel> messages) {
        messages.forEach(this::processSingleMessage);
    }

    private void processSingleMessage(InventoryConfirmationRequestAvroModel message) {
        try {
            handleInventoryConfirmationRequest(message);
        } catch (Exception exception) {
            handleProcessingException(message, exception);
        }
    }

    private void handleInventoryConfirmationRequest(
            InventoryConfirmationRequestAvroModel message
    ) {
        InventoryConfirmationRequest inventoryConfirmationRequest =
                inventoryMessageMapper.inventoryConfirmationRequestAvroModelToInventoryConfirmationRequest(message);

        switch (message.getInventoryOrderStatus()) {
            case PAID -> {
                log.info("Processing paid inventory confirmation request for order id: {}", message.getOrderId());
                //TODO: Handle paid inventory confirmation request
            }
            default -> {
                log.warn("Unknown inventory order status: {} for order id: {}", message.getInventoryOrderStatus(), message.getOrderId());
            }
        }
    }

    private void handleProcessingException(
            InventoryConfirmationRequestAvroModel message,
            Exception exception
    ) {
        if (exception instanceof NotFoundInventoryException) {
            log.error("No inventory found for order id: {} and inventory id: {}",
                    message.getOrderId(), ((NotFoundInventoryException) exception).getInventoryId());
            return;
        }

        if (isUniqueConstraintViolation(exception)) {
            log.error("Caught unique constraint exception with sql state: {} " +
                            "in InventoryConfirmationRequestKafkaListener for order id: {}",
                    MYSQL_UNIQUE_CONSTRAINT_VIOLATION, message.getOrderId());
            return;
        }

        throw new InventoryDomainException(
                new ResponseCode("Failed to process inventory confirmation request: " + exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception
        );
    }

    private boolean isUniqueConstraintViolation(Exception exception) {
        if (!(exception instanceof DataAccessException dataAccessException)) {
            return false;
        }

        SQLException sqlException = (SQLException) dataAccessException.getRootCause();
        return sqlException != null && MYSQL_UNIQUE_CONSTRAINT_VIOLATION.equals(sqlException.getSQLState());
    }
}
