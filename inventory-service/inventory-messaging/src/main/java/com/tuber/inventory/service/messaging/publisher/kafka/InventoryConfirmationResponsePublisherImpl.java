package com.tuber.inventory.service.messaging.publisher.kafka;

import com.tuber.application.helper.CommonHelper;
import com.tuber.inventory.service.domain.configuration.InventoryServiceConfigurationData;
import com.tuber.inventory.service.domain.outbox.model.InventoryConfirmationResponsePayload;
import com.tuber.inventory.service.domain.outbox.model.OrderOutboxMessage;
import com.tuber.inventory.service.domain.ports.output.message.publisher.InventoryConfirmationResponsePublisher;
import com.tuber.inventory.service.messaging.mapper.InventoryMessageMapper;
import com.tuber.kafka.order.avro.model.InventoryConfirmationResponseAvroModel;
import com.tuber.kafka.producer.KafkaProducer;
import com.tuber.kafka.producer.KafkaProducerHelper;
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
public class InventoryConfirmationResponsePublisherImpl implements InventoryConfirmationResponsePublisher {
    CommonHelper commonHelper;
    KafkaProducerHelper kafkaProducerHelper;
    InventoryMessageMapper inventoryMessageMapper;
    KafkaProducer<String, InventoryConfirmationResponseAvroModel> kafkaProducer;
    InventoryServiceConfigurationData inventoryServiceConfigurationData;

    @Override
    public void publish(OrderOutboxMessage orderOutboxMessage, BiConsumer<OrderOutboxMessage, OutboxStatus> onSuccessOutbox) {
        InventoryConfirmationResponsePayload inventoryConfirmationResponsePayload = commonHelper.mapJsonStringIntoClass(
                orderOutboxMessage.getPayload(),
                InventoryConfirmationResponsePayload.class
        );
        UUID sagaId = orderOutboxMessage.getSagaId();
        log.info("Received OrderInventoryOutboxMessage has order with id: {} and saga id: {}",
                inventoryConfirmationResponsePayload.getOrderId(),
                sagaId
        );

        try {
            InventoryConfirmationResponseAvroModel inventoryConfirmationResponseAvroModel =
                    inventoryMessageMapper.inventoryConfirmationResponsePayloadToInventoryConfirmationResponseAvroModel(
                            inventoryConfirmationResponsePayload,
                            sagaId
                    );
            kafkaProducer.send(
                    inventoryServiceConfigurationData.getInventoryConfirmationResponseTopicName(),
                    sagaId.toString(),
                    inventoryConfirmationResponseAvroModel,
                    kafkaProducerHelper.getOnSuccessKafka(
                            inventoryServiceConfigurationData.getInventoryConfirmationResponseTopicName(),
                            inventoryConfirmationResponseAvroModel,
                            orderOutboxMessage,
                            onSuccessOutbox
                    )
            );
        } catch (Exception exception) {
            log.error("Something wrong happened while sending OrderInventoryOutboxMessage" +
                            " to message bus with order id: {} and saga id: {}, error: {}",
                    inventoryConfirmationResponsePayload.getOrderId(),
                    sagaId,
                    exception.getMessage()
            );
        }
    }
}
