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
import com.tuber.order.service.domain.valueobject.OrderItemId;
import com.tuber.order.service.domain.valueobject.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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

    protected Set<OrderItem> generateOrderItems(Set<InternalInventoryDetailResponseData> productDetails, List<OrderItemDTO> orderItemDTOList) {
        Set<OrderItemDTO> sanitizedOrderItemsDTO = new HashSet<>(orderItemDTOList.stream()
                .collect(Collectors.toMap(orderItem -> orderItem.getProductId() + orderItem.getSku(), Function.identity(), (orderItem1, orderItem2) -> {
                    orderItem1.setQuantity((orderItem1.getQuantity() + orderItem2.getQuantity()));
                    return orderItem1;
                })).values());

        return productDetails.stream().map(inventory -> {
            OrderItem orderItem = inventoryResponseDataToOrderItem(inventory);
            orderItem.setQuantity(sanitizedOrderItemsDTO.stream()
                    .filter(orderItemDTO -> orderItemDTO.getProductId().equals(inventory.getProduct().getId()) && orderItemDTO.getSku().equals(inventory.getSku()))
                    .findFirst()
                    .map(OrderItemDTO::getQuantity)
                    .orElse(0));
            return orderItem;
        }).collect(Collectors.toSet());
    }

    public OrderEntity createOrderCommandToOrderEntity(CreateOrderCommand createOrderCommand, Set<InternalInventoryDetailResponseData> productDetails, String buyer) {
        return OrderEntity.builder()
                .buyer(buyer)
                .orderItems(generateOrderItems(productDetails, createOrderCommand.getOrderItems()))
                .vouchers(createOrderCommand.getVoucherIds().stream().map(Voucher::new).collect(Collectors.toSet()))
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
