package com.tuber.payment.service.messaging.listener.kafka;

import com.tuber.domain.constant.response.code.ResponseCode;
import com.tuber.kafka.order.avro.model.PaymentOrderStatus;
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

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${config-data.payment-request-topic-name}")
    public void receive(@Payload List<PaymentRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} messages with keys {}, partitions {} and offsets {} were found.",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString()
        );

        messages.forEach(messageModel -> {
            try {
                if (messageModel.getPaymentOrderStatus() == PaymentOrderStatus.PENDING) {
                    log.info("Payment for order with order id: {} is being processed", messageModel.getOrderId());
                    paymentRequestMessageListener.acceptPayment(
                            paymentMessageMapper.paymentRequestAvroModelToPaymentRequest(messageModel)
                    );
                }
                if (messageModel.getPaymentOrderStatus() == PaymentOrderStatus.CANCELLED) {
                    log.info("Payment for order with order id: {} is being cancelled", messageModel.getOrderId());
                    paymentRequestMessageListener.cancelPayment(
                            paymentMessageMapper.paymentRequestAvroModelToPaymentRequest(messageModel)
                    );
                }
            } catch (DataAccessException exception) {
                // Handle unique constraint violation
                SQLException sqlException = (SQLException) exception.getRootCause();
                if (sqlException != null && sqlException.getSQLState() != null &&
                        MYSQL_UNIQUE_CONSTRAINT_VIOLATION.equals(sqlException.getSQLState())) {
                    log.error("Unique constraint violation (SQLState: {}) for order id: {}",
                            sqlException.getSQLState(), messageModel.getOrderId());
                } else {
                    String errorMessage = String.format("DataAccessException is thrown in kafka listener: %s", exception.getMessage());
                    throw new PaymentDomainException(new ResponseCode(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR.value(), exception);
                }
            } catch (NotFoundPaymentException exception) {
                // Handle payment not found exception
                log.error("No payment was found for order id: {}. Error: {}",
                        messageModel.getOrderId(), exception.getMessage());
            }
        });
    }
}
