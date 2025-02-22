package com.tuber.inventory.service.domain.mapper;

import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.WarehousesListResponseData;
import com.tuber.inventory.service.domain.entity.Warehouse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class WarehouseMapper {
    public abstract Warehouse createWarehouseCommandToWarehouse(CreateWarehouseCommand createWarehouseCommand);
    public abstract WarehouseResponseData warehouseToWarehouseResponseData(Warehouse warehouse);
    protected UUID map(UniqueUUID id) {
        return id.getValue();
    }
    public WarehousesListResponseData warehousesListToWarehousesListResponseData(List<Warehouse> warehouseList) {
        return WarehousesListResponseData.builder()
                .warehouses(warehouseList.stream().map(this::warehouseToWarehouseResponseData).toList())
                .total(warehouseList.size())
                .build();
    }
}
