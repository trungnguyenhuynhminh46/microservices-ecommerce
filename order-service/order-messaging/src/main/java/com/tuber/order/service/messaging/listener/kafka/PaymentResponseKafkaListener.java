package com.tuber.order.service.messaging.listener.kafka;

import com.tuber.domain.constant.response.code.ResponseCode;
import com.tuber.domain.exception.OrderDomainException;
import com.tuber.kafka.order.avro.model.PaymentResponseAvroModel;
import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;
import com.tuber.order.service.domain.exception.NotFoundOrderException;
import com.tuber.order.service.domain.ports.input.message.listener.inventory.PaymentResponseListener;
import com.tuber.order.service.messaging.mapper.OrderPaymentMessageMapper;
import com.tuber.ordering.system.kafka.consumer.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {
    PaymentResponseListener paymentResponseListener;
    OrderPaymentMessageMapper orderPaymentMessageMapper;

    @Override
    @KafkaListener(
            id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${config-data.payment-response-topic-name}"
    )
    public void receive(
            @Payload List<PaymentResponseAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        logReceivedMessages(messages, keys, partitions, offsets);
        processMessages(messages);
    }

    private void logReceivedMessages(List<PaymentResponseAvroModel> messages,
                                     List<String> keys,
                                     List<Integer> partitions,
                                     List<Long> offsets) {
        log.info("Received {} messages with keys: {}, partitions: {}, offsets: {}",
                messages.size(), keys, partitions, offsets);
    }

    private void processMessages(List<PaymentResponseAvroModel> messages) {
        messages.forEach(this::processSingleMessage);
    }

    private void processSingleMessage(PaymentResponseAvroModel message) {
        try {
            handlePaymentResponse(message);
        } catch (Exception exception) {
            handleProcessingException(message, exception);
        }
    }

    private void handlePaymentResponse(PaymentResponseAvroModel message) {
        PaymentResponse paymentResponse = orderPaymentMessageMapper.paymentResponseAvroModelToPaymentResponse(message);
        switch (message.getPaymentStatus()) {
            case COMPLETED -> {
                log.info("Continuing processing order after successfully payment for order with order id: {}", message.getOrderId());
                paymentResponseListener.updateOrderAfterPaymentCompleted(paymentResponse);
            }
            case CANCELLED, FAILED -> {
                log.info("Reverting order after unsuccessfully payment for order with order id: {}", message.getOrderId());
                paymentResponseListener.updateOrderAfterPaymentCancelled(paymentResponse);
            }
        }
    }

    private void handleProcessingException(PaymentResponseAvroModel message, Exception exception) {
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
                new ResponseCode(String.format("Failed to handle payment response for order wiht order id: %s", message.getOrderId())),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception
        );
    }
}
