package com.tuber.payment.service.messaging.publisher.kafka;

import com.tuber.application.helper.CommonHelper;
import com.tuber.kafka.order.avro.model.PaymentResponseAvroModel;
import com.tuber.kafka.producer.KafkaProducer;
import com.tuber.kafka.producer.KafkaProducerHelper;
import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.config.PaymentServiceConfigurationData;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import com.tuber.payment.service.domain.ports.output.message.publisher.PaymentResponseMessagePublisher;
import com.tuber.payment.service.messaging.mapper.PaymentMessageMapper;
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
public class PaymentEventKafkaPublisher implements PaymentResponseMessagePublisher {
    CommonHelper commonHelper;
    KafkaProducerHelper kafkaProducerHelper;
    PaymentMessageMapper paymentMessageMapper;
    KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    PaymentServiceConfigurationData paymentServiceConfigurationData;

    @Override
    public void publish(OutboxOrderMessage outboxOrderMessage, BiConsumer<OutboxOrderMessage, OutboxStatus> onSuccessOutbox) {
        PaymentResponsePayload paymentResponsePayload = commonHelper.mapJsonStringIntoClass(
                outboxOrderMessage.getPayload(),
                PaymentResponsePayload.class
        );
        UUID sagaId = outboxOrderMessage.getSagaId();
        log.info("Received OrderPaymentOutboxMessage has order with id: {} and saga id: {}",
                paymentResponsePayload.getOrderId(),
                sagaId
        );
        try {
            PaymentResponseAvroModel paymentResponseAvroModel = paymentMessageMapper.paymentResponsePayloadToPaymentResponseAvroModel(paymentResponsePayload, sagaId);
            kafkaProducer.send(
                    paymentServiceConfigurationData.getPaymentResponseTopicName(),
                    sagaId.toString(),
                    paymentResponseAvroModel,
                    kafkaProducerHelper.getOnSuccessKafka(
                            paymentServiceConfigurationData.getPaymentResponseTopicName(),
                            paymentResponseAvroModel,
                            outboxOrderMessage,
                            onSuccessOutbox
                    )
            );
        } catch (Exception exception) {
            log.error("Something wrong happened while sending OrderPaymentOutboxMessage" +
                            " to message bus with order id: {} and saga id: {}, error: {}",
                    paymentResponsePayload.getOrderId(),
                    sagaId,
                    exception.getMessage()
            );
        }
    }
}
