package com.tuber.order.service.messaging.publisher.kafka;

import com.tuber.application.helper.CommonHelper;
import com.tuber.kafka.order.avro.model.InventoryConfirmationRequestAvroModel;
import com.tuber.kafka.producer.KafkaProducer;
import com.tuber.kafka.producer.KafkaProducerHelper;
import com.tuber.order.service.domain.config.OrderServiceConfigurationData;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationEventPayload;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.ports.output.message.publisher.inventory.InventoryConfirmationRequestPublisher;
import com.tuber.order.service.messaging.mapper.InventoryConfirmationMessageMapper;
import com.tuber.outbox.OutboxStatus;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiConsumer;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryConfirmationRequestPublisherImpl implements InventoryConfirmationRequestPublisher {
    CommonHelper commonHelper;
    KafkaProducerHelper kafkaProducerHelper;
    InventoryConfirmationMessageMapper inventoryConfirmationMessageMapper;
    KafkaProducer<String, InventoryConfirmationRequestAvroModel> kafkaProducer;
    OrderServiceConfigurationData orderServiceConfigurationData;

    @Override
    public void publish(InventoryConfirmationOutboxMessage inventoryConfirmationRequestMessage, BiConsumer<InventoryConfirmationOutboxMessage, OutboxStatus> outboxCallback) {
        InventoryConfirmationEventPayload inventoryConfirmationEventPayload = commonHelper.mapJsonStringIntoClass(
                inventoryConfirmationRequestMessage.getPayload(),
                InventoryConfirmationEventPayload.class
        );
        UUID sagaId = inventoryConfirmationRequestMessage.getSagaId();
        log.info("Received InventoryConfirmationOutboxMessage has order with id: {} and saga id: {}",
                inventoryConfirmationEventPayload.getOrderId(),
                sagaId
        );
        try {
            InventoryConfirmationRequestAvroModel inventoryConfirmationRequestAvroModel =
                    inventoryConfirmationMessageMapper
                            .inventoryConfirmationEventToInventoryConfirmationRequestAvroModel(inventoryConfirmationEventPayload, sagaId);
            kafkaProducer.send(
                    orderServiceConfigurationData.getInventoryConfirmationRequestTopicName(),
                    sagaId.toString(),
                    inventoryConfirmationRequestAvroModel,
                    kafkaProducerHelper.getOnSuccessKafka(
                            orderServiceConfigurationData.getInventoryConfirmationRequestTopicName(),
                            inventoryConfirmationRequestAvroModel,
                            inventoryConfirmationRequestMessage,
                            outboxCallback
                    )
            );
            log.info("InventoryConfirmationOutboxMessage with order id: {} and saga id: {} sent to message bus",
                    inventoryConfirmationEventPayload.getOrderId(),
                    sagaId
            );
        } catch (Exception e) {
            log.error("Something wrong happened while sending InventoryConfirmationOutboxMessage" +
                            " to message bus with order id: {} and saga id: {}, error: {}",
                    inventoryConfirmationEventPayload.getOrderId(),
                    sagaId,
                    e.getMessage()
            );
        }
    }
}
