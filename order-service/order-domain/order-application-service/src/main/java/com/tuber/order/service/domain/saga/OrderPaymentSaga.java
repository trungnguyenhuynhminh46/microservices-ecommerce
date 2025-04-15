package com.tuber.order.service.domain.saga;

import com.tuber.application.mapper.StatusMapper;
import com.tuber.domain.valueobject.enums.OrderStatus;
import com.tuber.domain.valueobject.enums.PaymentStatus;
import com.tuber.order.service.domain.OrderDomainService;
import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCancelEvent;
import com.tuber.order.service.domain.event.OrderPaymentCompleteEvent;
import com.tuber.order.service.domain.helper.CommonOrderHelper;
import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.ports.output.repository.OrderRepository;
import com.tuber.order.service.domain.ports.output.repository.OutboxPaymentRepository;
import com.tuber.saga.SagaStatus;
import com.tuber.saga.SagaStep;
import com.tuber.saga.order.SagaName;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderPaymentSaga implements SagaStep<PaymentResponse> {
    OutboxPaymentRepository outboxPaymentRepository;
    OrderDomainService orderDomainService;
    CommonOrderHelper commonOrderHelper;
    OrderRepository orderRepository;
    StatusMapper statusMapper;

    protected OrderPaymentCompleteEvent completePaymentForOrder(OrderEntity order) {
        log.info("Completing payment for order with order id: {}", order.getId());
        OrderPaymentCompleteEvent event = orderDomainService.completeOrderPayment(order);
        orderRepository.save(order);
        return event;
    }

    protected OrderCancelEvent cancelOrder(OrderEntity order, List<String> failureMessages) {
        log.info("Cancelling order with order id: {}", order.getId());
        OrderCancelEvent event = orderDomainService.cancelOrder(order, failureMessages);
        orderRepository.save(order);
        return event;
    }

    protected void updatePaymentOutboxMessage(
            PaymentOutboxMessage message,
            OrderStatus orderStatus
    ) {
        message.setProcessedAt(LocalDate.now());
        message.setOrderStatus(orderStatus);
        message.setSagaStatus(statusMapper.orderStatusToSagaStatus(orderStatus));
        outboxPaymentRepository.save(message);
    }

    //TODO: Implement this method
    @Override
    public void process(PaymentResponse data) {
        Optional<PaymentOutboxMessage> response = outboxPaymentRepository
                .findBySagaIdAndTypeAndSagaStatuses(data.getSagaId(), SagaName.ORDER_PROCESSING_SAGA.name(), SagaStatus.STARTED);
        if (response.isEmpty()) {
            log.error("Payment outbox message with saga id: {} and saga status: {} could not be found!",
                    data.getSagaId(), SagaStatus.STARTED);
            return;
        }
        PaymentOutboxMessage paymentOutboxMessage = response.get();

        OrderPaymentCompleteEvent orderPaymentCompleteEvent = completePaymentForOrder(commonOrderHelper.verifyOrderExists(data.getOrderId()));

        updatePaymentOutboxMessage(
                paymentOutboxMessage,
                orderPaymentCompleteEvent.getOrder().getOrderStatus()
        );

        // Save inventory approval outbox message

        log.info("Order with order id: {} is paid", data.getOrderId());
    }

    //TODO: Implement this method
    @Override
    public void rollback(PaymentResponse data) {
        Optional<PaymentOutboxMessage> response =
                outboxPaymentRepository.findBySagaIdAndTypeAndSagaStatuses(
                        data.getSagaId(),
                        SagaName.ORDER_PROCESSING_SAGA.name(),
                        statusMapper.paymentStatusToSagaStatus(data.getPaymentStatus())
                );
        if (response.isEmpty()) {
            log.error("Payment outbox message with saga id: {} and saga status: {} could not be found!",
                    data.getSagaId(), statusMapper.paymentStatusToSagaStatus(data.getPaymentStatus()));
            return;
        }
        PaymentOutboxMessage paymentOutboxMessage = response.get();

        OrderCancelEvent orderCancelEvent =
                cancelOrder(
                        commonOrderHelper.verifyOrderExists(data.getOrderId()),
                        data.getFailureMessages()
                );

        updatePaymentOutboxMessage(
                paymentOutboxMessage,
                orderCancelEvent.getOrder().getOrderStatus()
        );

        if (data.getPaymentStatus() == PaymentStatus.CANCELLED) {
            // Save inventory approval outbox message in case payment failed
        }

        log.info("Payment of order with id: {} is cancelled", data.getOrderId());
    }
}
