package com.tuber.order.service.domain.mapper;

import com.tuber.domain.entity.Warehouse;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.domain.dto.http.client.inventory.InternalInventoryDetailResponseData;
import com.tuber.order.service.domain.dto.order.CreateOrderCommand;
import com.tuber.order.service.domain.dto.order.OrderResponseData;
import com.tuber.order.service.domain.dto.shared.OrderItemDTO;
import com.tuber.order.service.domain.dto.shared.OrderItemDetailDTO;
import com.tuber.order.service.domain.dto.shared.ProductIdWithSkuDTO;
import com.tuber.order.service.domain.entity.OrderEntity;
import com.tuber.order.service.domain.entity.OrderItem;
import com.tuber.order.service.domain.entity.Voucher;
import com.tuber.order.service.domain.event.OrderCreatedEvent;
import com.tuber.order.service.domain.outbox.model.payment.OrderPaymentEventPayload;
import com.tuber.order.service.domain.valueobject.OrderItemId;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;
import com.tuber.saga.SagaStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public Set<ProductIdWithSkuDTO> orderIdDTOsToSetOfProductIdWithSkuDTO(List<OrderItemDTO> orderItems) {
        return orderItems.stream().map(
                orderItem -> ProductIdWithSkuDTO.builder()
                        .productId(orderItem.getProductId())
                        .sku(orderItem.getSku())
                        .build()
        ).collect(Collectors.toSet());
    }

    protected Set<OrderItem> generateOrderItems(Set<InternalInventoryDetailResponseData> productDetails,
                                                List<OrderItemDTO> orderItemDTOList) {
        Map<String, OrderItemDTO> sanitizedOrderItemsDTOMap = orderItemDTOList.stream()
                .collect(Collectors.toMap(
                        item -> item.getProductId() + "|" + item.getSku(),
                        Function.identity(),
                        (item1, item2) -> {
                            item1.setQuantity(item1.getQuantity() + item2.getQuantity());
                            return item1;
                        }));

        return productDetails.stream()
                .map(inventory -> {
                    OrderItem orderItem = inventoryResponseDataToOrderItem(inventory);
                    String key = inventory.getProduct().getId() + "|" + inventory.getSku();
                    OrderItemDTO matchedDTO = sanitizedOrderItemsDTOMap.get(key);
                    orderItem.setQuantity(matchedDTO != null ? matchedDTO.getQuantity() : 0);
                    return orderItem;
                })
                .collect(Collectors.toSet());
    }

    public OrderEntity createOrderCommandToOrderEntity(CreateOrderCommand createOrderCommand, Set<Voucher> usedVouchers, Set<InternalInventoryDetailResponseData> productDetails, String buyer) {
        return OrderEntity.builder()
                .buyer(buyer)
                .orderItems(generateOrderItems(productDetails, createOrderCommand.getOrderItems()))
                .vouchers(usedVouchers)
                .orderStatus(OrderStatus.PROCESSING)
                .build();
    }

    public abstract OrderResponseData orderEntityToOrderResponseData(OrderEntity order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouse", source = "warehouseId")
    protected abstract OrderItem inventoryResponseDataToOrderItem(InternalInventoryDetailResponseData inventoryDetail);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    protected abstract OrderItemDetailDTO orderItemToOrderItemDetailDTO(OrderItem orderItem);

    //TODO: Implement this method
    public abstract OrderPaymentEventPayload orderCreatedEventToOrderPaymentEventPayload(OrderCreatedEvent orderCreatedEvent);

    public SagaStatus orderStatusToSagaStatus(OrderStatus orderStatus) {
        return switch (orderStatus) {
            case PAID -> SagaStatus.PROCESSING;
            case APPROVED -> SagaStatus.SUCCEEDED;
            case CANCELLING -> SagaStatus.COMPENSATING;
            case CANCELLED -> SagaStatus.COMPENSATED;
            default -> SagaStatus.STARTED;
        };
    }

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected Long mapId(OrderItemId id) {
        return id.getValue();
    }

    protected UniqueUUID mapId(UUID id) {
        return new UniqueUUID(id);
    }

    protected Warehouse mapWarehouse(UUID warehouseId) {
        return new Warehouse(warehouseId);
    }

    protected Money mapMoney(BigDecimal amount) {
        return new Money(amount);
    }

    protected BigDecimal mapMoney(Money money) {
        return money.getAmount();
    }
}
