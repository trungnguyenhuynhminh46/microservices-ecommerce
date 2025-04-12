package com.tuber.order.service.messaging.publisher.kafka;

import com.tuber.application.helper.CommonHelper;
import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.kafka.producer.KafkaProducer;
import com.tuber.kafka.producer.KafkaProducerHelper;
import com.tuber.order.service.domain.config.OrderServiceConfigurationData;
import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.outbox.model.payment.PaymentPayload;
import com.tuber.order.service.domain.ports.output.message.publisher.payment.PaymentMessageRequestPublisher;
import com.tuber.order.service.messaging.mapper.OrderPaymentMessageMapper;
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
public class PaymentMessageRequestPublisherImpl implements PaymentMessageRequestPublisher {
    CommonHelper commonHelper;
    KafkaProducerHelper kafkaProducerHelper;
    OrderPaymentMessageMapper orderPaymentMessageMapper;
    KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    OrderServiceConfigurationData orderServiceConfigurationData;

    @Override
    public void publish(PaymentOutboxMessage paymentOutboxMessage, BiConsumer<PaymentOutboxMessage, OutboxStatus> onSuccessOutbox) {
        PaymentPayload paymentPayload = commonHelper.mapJsonStringIntoClass(
                paymentOutboxMessage.getPayload(),
                PaymentPayload.class
        );
        UUID sagaId = paymentOutboxMessage.getSagaId();
        log.info("Received OrderPaymentOutboxMessage has order with id: {} and saga id: {}",
                paymentPayload.getOrderId(),
                sagaId
        );
        try {
            PaymentRequestAvroModel PaymentRequestAvroModel =
                    orderPaymentMessageMapper.orderPaymentPayloadToPaymentRequestAvroModel(paymentPayload);
            kafkaProducer.send(
                    orderServiceConfigurationData.getPaymentRequestTopicName(),
                    sagaId.toString(),
                    PaymentRequestAvroModel,
                    kafkaProducerHelper.getOnSuccessKafka(
                            orderServiceConfigurationData.getPaymentRequestTopicName(),
                            PaymentRequestAvroModel,
                            paymentOutboxMessage,
                            onSuccessOutbox
                    )
            );
        } catch (Exception e) {
            log.error("Something wrong happened while sending OrderPaymentOutboxMessage" +
                            " to message bus with order id: {} and saga id: {}, error: {}",
                    paymentPayload.getOrderId(),
                    sagaId,
                    e.getMessage()
            );
        }
    }
}
