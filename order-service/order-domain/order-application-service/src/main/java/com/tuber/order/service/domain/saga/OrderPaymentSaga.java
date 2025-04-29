package com.tuber.order.service.domain.saga;

import com.tuber.application.mapper.StatusMapper;
import com.tuber.domain.valueobject.enums.PaymentStatus;
import com.tuber.order.service.domain.OrderDomainService;
import com.tuber.order.service.domain.dto.message.broker.PaymentResponse;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderCancelEvent;
import com.tuber.order.service.domain.event.OrderPaymentCompleteEvent;
import com.tuber.order.service.domain.helper.CommonOrderHelper;
import com.tuber.order.service.domain.mapper.OutboxMessageMapper;
import com.tuber.order.service.domain.outbox.model.payment.PaymentOutboxMessage;
import com.tuber.order.service.domain.outbox.scheduler.inventory.InventoryConfirmationOutboxHelper;
import com.tuber.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.tuber.order.service.domain.ports.output.repository.OrderRepository;
import com.tuber.order.service.domain.ports.output.repository.outbox.OutboxPaymentRepository;
import com.tuber.outbox.OutboxStatus;
import com.tuber.saga.SagaStatus;
import com.tuber.saga.SagaStep;
import com.tuber.saga.order.SagaName;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    OutboxMessageMapper outboxMessageMapper;
    InventoryConfirmationOutboxHelper inventoryConfirmationOutboxHelper;
    PaymentOutboxHelper paymentOutboxHelper;

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

    @Override
    @Transactional
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

        paymentOutboxHelper.updatePaymentOutboxMessage(
                paymentOutboxMessage,
                orderPaymentCompleteEvent.getOrder().getOrderStatus()
        );

        inventoryConfirmationOutboxHelper.saveInventoryConfirmationOutboxMessage(
                outboxMessageMapper.orderPaymentCompleteEventToInventoryConfirmationEventPayload(orderPaymentCompleteEvent),
                data.getSagaId(),
                statusMapper.orderStatusToSagaStatus(orderPaymentCompleteEvent.getOrder().getOrderStatus()),
                OutboxStatus.STARTED,
                orderPaymentCompleteEvent.getOrder().getOrderStatus()
        );

        log.info("Order with order id: {} is paid", data.getOrderId());
    }


    @Override
    @Transactional
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

        paymentOutboxHelper.updatePaymentOutboxMessage(
                paymentOutboxMessage,
                orderCancelEvent.getOrder().getOrderStatus()
        );

        if (data.getPaymentStatus() == PaymentStatus.CANCELLED) {
            inventoryConfirmationOutboxHelper.updateInventoryConfirmationOutboxMessage(
                    inventoryConfirmationOutboxHelper.verifyInventoryConfirmationOutboxMessageExists(data.getSagaId(), SagaStatus.COMPENSATING),
                    orderCancelEvent.getOrder().getOrderStatus()
            );
        }

        log.info("Payment of order with id: {} is cancelled", data.getOrderId());
    }
}
