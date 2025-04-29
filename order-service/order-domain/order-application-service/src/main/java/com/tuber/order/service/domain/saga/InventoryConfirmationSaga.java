package com.tuber.order.service.domain.saga;

import com.tuber.domain.valueobject.enums.OrderStatus;
import com.tuber.order.service.domain.OrderDomainService;
import com.tuber.order.service.domain.dto.message.broker.InventoryConfirmationResponse;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.event.OrderBeginCancellingEvent;
import com.tuber.order.service.domain.event.OrderConfirmValidForFulfillment;
import com.tuber.order.service.domain.helper.CommonOrderHelper;
import com.tuber.order.service.domain.mapper.OrderMapper;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationOutboxMessage;
import com.tuber.order.service.domain.outbox.scheduler.inventory.InventoryConfirmationOutboxHelper;
import com.tuber.order.service.domain.outbox.scheduler.payment.PaymentOutboxHelper;
import com.tuber.order.service.domain.ports.output.repository.OrderRepository;
import com.tuber.order.service.domain.ports.output.repository.outbox.InventoryConfirmationOutboxRepository;
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
public class InventoryConfirmationSaga implements SagaStep<InventoryConfirmationResponse> {
    InventoryConfirmationOutboxRepository inventoryConfirmationOutboxRepository;
    InventoryConfirmationOutboxHelper inventoryConfirmationOutboxHelper;
    PaymentOutboxHelper paymentOutboxHelper;
    OrderMapper orderMapper;
    OrderDomainService orderDomainService;
    OrderRepository orderRepository;
    CommonOrderHelper commonOrderHelper;

    protected OrderConfirmValidForFulfillment confirmOrderIsValidForFulfillment(OrderEntity order) {
        log.info("Confirming order is valid for fulfillment for order with order id: {}", order.getId());
        OrderConfirmValidForFulfillment event = orderDomainService.confirmValidForFulfillment(order);
        orderRepository.save(order);
        return event;
    }

    protected OrderBeginCancellingEvent beginCancellingOrder(OrderEntity order, List<String> failureMessages) {
        log.info("Beginning cancelling order with order id: {}", order.getId());
        OrderBeginCancellingEvent event = orderDomainService.beginCancellingOrder(order, failureMessages);
        orderRepository.save(order);
        return event;
    }

    @Override
    @Transactional
    public void process(InventoryConfirmationResponse data) {
        Optional<InventoryConfirmationOutboxMessage> response = inventoryConfirmationOutboxRepository
                .findBySagaIdAndTypeAndSagaStatusIn(data.getSagaId(), SagaName.ORDER_PROCESSING_SAGA.name(), SagaStatus.PROCESSING);
        if (response.isEmpty()) {
            log.info("Inventory confirmation outbox message with saga id: {} and saga status: {} could not be found!",
                    data.getSagaId(), SagaStatus.PROCESSING);
            return;
        }
        InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage = response.get();

        OrderConfirmValidForFulfillment event = confirmOrderIsValidForFulfillment(commonOrderHelper.verifyOrderExists(data.getOrderId()));
        OrderStatus updatedOrderStatus = event.getOrder().getOrderStatus();

        inventoryConfirmationOutboxHelper.updateInventoryConfirmationOutboxMessage(
                inventoryConfirmationOutboxMessage,
                updatedOrderStatus
        );

        paymentOutboxHelper.updatePaymentOutboxMessage(
                paymentOutboxHelper.verifyPaymentOutboxMessageExists(
                        inventoryConfirmationOutboxMessage.getSagaId(),
                        SagaStatus.PROCESSING
                ),
                updatedOrderStatus
        );
    }

    @Override
    @Transactional
    public void rollback(InventoryConfirmationResponse data) {
        Optional<InventoryConfirmationOutboxMessage> response = inventoryConfirmationOutboxRepository
                .findBySagaIdAndTypeAndSagaStatusIn(data.getSagaId(), SagaName.ORDER_PROCESSING_SAGA.name(), SagaStatus.PROCESSING);
        if (response.isEmpty()) {
            log.info("Inventory confirmation outbox message with saga id: {} and saga status: {} could not be found!",
                    data.getSagaId(), SagaStatus.PROCESSING);
            return;
        }
        InventoryConfirmationOutboxMessage inventoryConfirmationOutboxMessage = response.get();

        OrderBeginCancellingEvent event = beginCancellingOrder(commonOrderHelper.verifyOrderExists(data.getOrderId()), data.getFailureMessages());
        OrderStatus orderStatus = event.getOrder().getOrderStatus();

        inventoryConfirmationOutboxHelper.updateInventoryConfirmationOutboxMessage(
                inventoryConfirmationOutboxMessage,
                orderStatus
        );

        paymentOutboxHelper.savePaymentOutboxMessage(
                orderMapper.orderBeginCancellingEventToPaymentEventPayload(event),
                orderStatus,
                OutboxStatus.STARTED,
                inventoryConfirmationOutboxMessage.getSagaId()
        );

    }
}
