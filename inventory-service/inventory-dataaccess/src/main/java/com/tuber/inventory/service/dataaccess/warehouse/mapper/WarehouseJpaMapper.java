package com.tuber.inventory.service.dataaccess.warehouse.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.dataaccess.warehouse.entity.WarehouseJpaEntity;
import com.tuber.domain.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class WarehouseJpaMapper {
    @Mapping(source = "address.postalCode", target = "postalCode")
    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "location.latitude", target = "latitude")
    @Mapping(source = "location.longitude", target = "longitude")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd")
    public abstract WarehouseJpaEntity warehouseEntityToWarehouseJpaEntity(Warehouse warehouse);

    @Mapping(target = "address.id", expression = "java(generateUUID())")
    @Mapping(source = "postalCode", target = "address.postalCode")
    @Mapping(source = "street", target = "address.street")
    @Mapping(source = "city", target = "address.city")
    @Mapping(source = "latitude", target = "location.latitude")
    @Mapping(source = "longitude", target = "location.longitude")
    @Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd")
    public abstract Warehouse warehouseJpaEntityToWarehouseEntity(WarehouseJpaEntity warehouseJpaEntity);

    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }

    protected UniqueUUID map(UUID id) {
        return new UniqueUUID(id);
    }

    protected UUID generateUUID() {
        return UUID.randomUUID();
    }
}
