package com.tuber.inventory.service.domain.dto.warehouse;

import com.tuber.inventory.service.domain.dto.shared.AddressDTO;
import com.tuber.inventory.service.domain.dto.shared.CoordinateDTO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseResponseData {
    UUID id;
    String name;
    AddressDTO address;
    CoordinateDTO location;
    String description;
    String createdAt;
    String updatedAt;
}
