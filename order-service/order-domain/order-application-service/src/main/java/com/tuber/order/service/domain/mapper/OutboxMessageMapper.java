package com.tuber.order.service.domain.mapper;

import com.tuber.order.service.domain.entity.OrderItem;
import com.tuber.order.service.domain.event.OrderPaymentCompleteEvent;
import com.tuber.order.service.domain.outbox.model.inventory.InventoryConfirmationEventPayload;
import com.tuber.order.service.domain.outbox.model.inventory.ProductExportInformationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class OutboxMessageMapper {
    @Mapping(target = "orderId", source = "order.orderId")
    @Mapping(target = "totalPrice", source = "order.totalPrice.amount")
    @Mapping(target = "finalPrice", source = "order.finalPrice.amount")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "inventoryOrderStatus", expression = "java(com.tuber.domain.valueobject.enums.InventoryOrderStatus.PAID.name())")
    @Mapping(target = "productExportInformationDTOList", source = "order.orderItems")
    public abstract InventoryConfirmationEventPayload orderPaymentCompleteEventToInventoryConfirmationEventPayload(
            OrderPaymentCompleteEvent orderPaymentCompleteEvent
    );

    @Mapping(target = "productId", source = "orderItem.product.id.value")
    @Mapping(target = "basePrice", source = "orderItem.product.price.amount")
    @Mapping(target = "sku", source = "orderItem.sku")
    @Mapping(target = "requiredQuantity", source = "orderItem.quantity")
    protected abstract ProductExportInformationDTO orderItemToProductExportInformationDTO(OrderItem orderItem);
}
