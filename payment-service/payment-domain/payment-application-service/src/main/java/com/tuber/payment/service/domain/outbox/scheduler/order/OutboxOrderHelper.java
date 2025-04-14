package com.tuber.payment.service.domain.outbox.scheduler.order;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.constant.PaymentResponseCode;
import com.tuber.payment.service.domain.exception.PaymentDomainException;
import com.tuber.payment.service.domain.mapper.PaymentMapper;
import com.tuber.payment.service.domain.outbox.model.order.OutboxOrderMessage;
import com.tuber.payment.service.domain.outbox.model.order.PaymentResponsePayload;
import com.tuber.payment.service.domain.ports.output.repository.OutboxOrderRepository;
import com.tuber.domain.valueobject.enums.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static com.tuber.saga.order.SagaName.ORDER_PROCESSING_SAGA;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class OutboxOrderHelper {
    OutboxOrderRepository outboxOrderRepository;
    PaymentMapper paymentMapper;

    protected void save(OutboxOrderMessage outboxOrderMessage) {
        OutboxOrderMessage savedOutboxOrderMessage = outboxOrderRepository.save(outboxOrderMessage);
        if (savedOutboxOrderMessage == null) {
            log.error("Having error while saving outbox order OutboxOrderMessage");
            throw new PaymentDomainException(PaymentResponseCode.PAYMENT_SAVED_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), outboxOrderMessage.getId());
        }
        log.info("OutboxOrderMessage is saved with id: {}", savedOutboxOrderMessage.getId());
    }

    public void saveOrderOutboxMessage(PaymentResponsePayload paymentResponsePayload, PaymentStatus paymentStatus, OutboxStatus outboxStatus, UUID sagaId) {
        save(paymentMapper.paymentResponsePayloadToOutboxOrderMessage(
                paymentResponsePayload,
                paymentStatus,
                outboxStatus,
                sagaId
        ));
    }

    public Optional<OutboxOrderMessage> getCompletedOrderOutboxMessageByPaymentStatus(
            UUID sagaId,
            PaymentStatus paymentStatus
    ) {
        return outboxOrderRepository.findByTypeAndSagaIdAndPaymentStatusAndOutboxStatus(
                ORDER_PROCESSING_SAGA.name(),
                sagaId,
                paymentStatus,
                OutboxStatus.COMPLETED
        );
    }

    public void updateOutboxOrderMessageStatus(
            OutboxOrderMessage outboxOrderMessage,
            OutboxStatus outboxStatus
    ) {
        outboxOrderMessage.setOutboxStatus(outboxStatus);
        save(outboxOrderMessage);
        log.info("OutboxOrderMessage with id: {} is updated to status: {}", outboxOrderMessage.getId(), outboxStatus);
    }
}
