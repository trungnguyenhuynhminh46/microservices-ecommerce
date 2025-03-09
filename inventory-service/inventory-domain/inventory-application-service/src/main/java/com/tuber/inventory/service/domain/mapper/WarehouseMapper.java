package com.tuber.inventory.service.domain.mapper;

import com.tuber.application.mapper.JsonNullableMapper;
import com.tuber.domain.valueobject.Address;
import com.tuber.domain.valueobject.Coordinate;
import com.tuber.domain.valueobject.id.UniqueUUID;
import com.tuber.inventory.service.domain.dto.shared.AddressDTO;
import com.tuber.inventory.service.domain.dto.shared.CoordinateDTO;
import com.tuber.inventory.service.domain.dto.warehouse.CreateWarehouseCommand;
import com.tuber.inventory.service.domain.dto.warehouse.UpdateWarehouseInfoCommand;
import com.tuber.inventory.service.domain.dto.warehouse.WarehouseResponseData;
import com.tuber.inventory.service.domain.dto.warehouse.WarehousesListResponseData;
import com.tuber.domain.entity.Warehouse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.List;
import java.util.UUID;

@Mapper(uses = JsonNullableMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
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

    @Mapping(target = "id", ignore = true)
    public abstract Warehouse updateWarehouse(UpdateWarehouseInfoCommand updateWarehouseInformationCommand, @MappingTarget Warehouse warehouse);

    protected Address mapAddress(JsonNullable<AddressDTO> addressDTO) {
        AddressDTO address = addressDTO.orElse(null);
        return address == null ? null : new Address(UUID.randomUUID(), address.getPostalCode(), address.getStreet(), address.getCity());
    }

    protected Coordinate mapCoordinate(JsonNullable<CoordinateDTO> coordinateDTO) {
        CoordinateDTO coordinate = coordinateDTO.orElse(null);
        return coordinate == null ? null : new Coordinate(coordinate.getLatitude(), coordinate.getLongitude());
    }
}
