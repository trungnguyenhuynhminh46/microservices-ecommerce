package com.tuber.payment.service.messaging.listener.kafka;

import com.tuber.domain.constant.response.code.ResponseCode;
import com.tuber.kafka.order.avro.model.PaymentRequestAvroModel;
import com.tuber.ordering.system.kafka.consumer.KafkaConsumer;
import com.tuber.payment.service.domain.exception.NotFoundPaymentException;
import com.tuber.payment.service.domain.exception.PaymentDomainException;
import com.tuber.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import com.tuber.payment.service.messaging.mapper.PaymentMessageMapper;
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
public class PaymentRequestKafkaListener implements KafkaConsumer<PaymentRequestAvroModel> {
    private static final String MYSQL_UNIQUE_CONSTRAINT_VIOLATION = "23000";

    PaymentRequestMessageListener paymentRequestMessageListener;
    PaymentMessageMapper paymentMessageMapper;

    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${config-data.payment-request-topic-name}")
    public void receive(@Payload List<PaymentRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        logReceivedMessages(messages, keys, partitions, offsets);
        processMessages(messages);
    }

    private void logReceivedMessages(List<PaymentRequestAvroModel> messages,
                                     List<String> keys,
                                     List<Integer> partitions,
                                     List<Long> offsets) {
        log.info("Received {} messages with keys: {}, partitions: {}, offsets: {}",
                messages.size(), keys, partitions, offsets);
    }

    private void processMessages(List<PaymentRequestAvroModel> messages) {
        messages.forEach(this::processSingleMessage);
    }

    private void processSingleMessage(PaymentRequestAvroModel message) {
        try {
            handlePaymentRequest(message);
        } catch (Exception exception) {
            handleProcessingException(message, exception);
        }
    }

    private void handlePaymentRequest(PaymentRequestAvroModel message) {
        var paymentRequest = paymentMessageMapper.paymentRequestAvroModelToPaymentRequest(message);

        switch (message.getPaymentOrderStatus()) {
            case PENDING -> {
                log.info("Processing payment for order: {}", message.getOrderId());
                paymentRequestMessageListener.acceptPayment(paymentRequest);
            }
            case CANCELLED -> {
                log.info("Cancelling payment for order: {}", message.getOrderId());
                paymentRequestMessageListener.cancelPayment(paymentRequest);
            }
            default -> log.warn("Unsupported payment status: {} for order: {}",
                    message.getPaymentOrderStatus(), message.getOrderId());
        }
    }

    private void handleProcessingException(PaymentRequestAvroModel message, Exception exception) {
        if (exception instanceof NotFoundPaymentException) {
            log.error("Payment not found for order: {}. Error: {}",
                    message.getOrderId(), exception.getMessage());
            return;
        }

        if (isUniqueConstraintViolation(exception)) {
            log.error("Unique constraint violation for order: {}", message.getOrderId());
            return;
        }

        throw new PaymentDomainException(
                new ResponseCode("Failed to process payment request: " + exception.getMessage()),
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
