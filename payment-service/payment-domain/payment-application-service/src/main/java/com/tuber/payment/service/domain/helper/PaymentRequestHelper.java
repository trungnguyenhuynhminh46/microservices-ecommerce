package com.tuber.payment.service.domain.helper;

import com.tuber.outbox.OutboxStatus;
import com.tuber.payment.service.domain.PaymentDomainService;
import com.tuber.payment.service.domain.dto.message.broker.PaymentRequest;
import com.tuber.payment.service.domain.entity.CreditEntry;
import com.tuber.payment.service.domain.entity.CreditHistory;
import com.tuber.payment.service.domain.entity.Payment;
import com.tuber.payment.service.domain.event.PaymentEvent;
import com.tuber.payment.service.domain.mapper.PaymentMapper;
import com.tuber.payment.service.domain.outbox.scheduler.order.OutboxOrderHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO: Implement this class
@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentRequestHelper {
    PaymentMapper paymentMapper;
    PaymentCommonHelper paymentCommonHelper;
    PaymentDomainService paymentDomainService;
    OutboxOrderHelper outboxOrderHelper;

    //TODO: Implement this method
    protected boolean paymentIsCompleted(PaymentRequest paymentRequest) {
        return false;
    }

    //TODO: Implement this method
    protected boolean paymentIsCancelled(PaymentRequest paymentRequest) {
        return false;
    }

    //TODO: Implement this method
    protected void persistCreditInformation(Payment payment,
                                            CreditEntry creditEntry,
                                            List<CreditHistory> creditHistories,
                                            List<String> failureMessages) {
        return;
    }

    @Transactional
    public void persistPayment(PaymentRequest paymentRequest) {
        if (paymentIsCompleted(paymentRequest)) {
            log.info("The outbox message with saga id: {} is already saved to database!",
                    paymentRequest.getSagaId());
            return;
        }
        log.info("Received payment complete event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentMapper.paymentRequestToPayment(paymentRequest);
        CreditEntry creditEntry = paymentCommonHelper.verifyCreditEntryOfCustomerExists(paymentRequest.getCustomerId());
        List<CreditHistory> creditHistories = paymentCommonHelper.verifyCreditHistoryOfCustomerExists(paymentRequest.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndInitializePayment(
                payment, creditEntry, creditHistories, failureMessages);
        persistCreditInformation(payment, creditEntry, creditHistories, failureMessages);
        outboxOrderHelper.saveOrderOutboxMessage(
                paymentMapper.paymentEventToPaymentResponseEventPayload(paymentEvent),
                paymentEvent.getPayment().getPaymentStatus(),
                OutboxStatus.STARTED,
                paymentRequest.getSagaId()
        );

    }

    @Transactional
    public void persistCancelledPayment(PaymentRequest paymentRequest) {
        if (paymentIsCancelled(paymentRequest)) {
            log.info("The outbox message with saga id: {} is already saved to database!",
                    paymentRequest.getSagaId());
            return;
        }
        log.info("Received payment rollback event for order id: {}", paymentRequest.getOrderId());
        Payment payment = paymentCommonHelper.verifyPaymentOfOrderExists(UUID.fromString(paymentRequest.getOrderId()));
        CreditEntry creditEntry = paymentCommonHelper.verifyCreditEntryOfCustomerExists(paymentRequest.getCustomerId());
        List<CreditHistory> creditHistories = paymentCommonHelper.verifyCreditHistoryOfCustomerExists(paymentRequest.getCustomerId());
        List<String> failureMessages = new ArrayList<>();
        PaymentEvent paymentEvent = paymentDomainService.validateAndCancelPayment(
                payment, creditEntry, creditHistories, failureMessages);
        persistCreditInformation(payment, creditEntry, creditHistories, failureMessages);
        outboxOrderHelper.saveOrderOutboxMessage(
                paymentMapper.paymentEventToPaymentResponseEventPayload(paymentEvent),
                paymentEvent.getPayment().getPaymentStatus(),
                OutboxStatus.STARTED,
                paymentRequest.getSagaId()
        );
    }
}
