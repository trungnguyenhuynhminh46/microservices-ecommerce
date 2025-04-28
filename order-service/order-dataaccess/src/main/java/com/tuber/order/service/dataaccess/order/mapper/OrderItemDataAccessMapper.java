package com.tuber.order.service.dataaccess.order.mapper;

import com.tuber.domain.entity.Warehouse;
import com.tuber.domain.valueobject.Money;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.order.service.dataaccess.order.entity.OrderItemJpaEntity;
import com.tuber.order.service.dataaccess.order.entity.OrderJpaEntity;
import com.tuber.order.service.domain.entity.OrderItem;
import com.tuber.order.service.domain.entity.Product;
import com.tuber.order.service.domain.valueobject.OrderItemId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class OrderItemDataAccessMapper {
    @Mapping(target = "order", source = "orderId")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "warehouseId", source = "warehouse.id")
    public abstract OrderItemJpaEntity orderItemEntityToOrderItemJpaEntity(OrderItem orderItem);
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "warehouse", source = "warehouseId")
    @Mapping(target = "inventoryId", ignore = true)
    public abstract OrderItem orderItemJpaEntityToOrderItemEntity(OrderItemJpaEntity orderItemJpaEntity);

    protected Long mapId(OrderItemId id) {
        return id.getValue();
    }

    protected UUID mapId(UniqueUUID id) {
        return id.getValue();
    }

    protected OrderJpaEntity mapOrder(UUID orderId) {
        return OrderJpaEntity.builder().id(orderId).build();
    }

    protected BigDecimal mapMoney(Money money) {
        return money.getAmount();
    }

    protected Money mapMoney(BigDecimal money) {
        return new Money(money);
    }

    protected Product mapProduct(UUID productId) {
        return Product.builder().id(productId).build();
    }

    protected Warehouse mapWarehouse(UUID warehouseId) {
        return Warehouse.builder().id(warehouseId).build();
    }
}
