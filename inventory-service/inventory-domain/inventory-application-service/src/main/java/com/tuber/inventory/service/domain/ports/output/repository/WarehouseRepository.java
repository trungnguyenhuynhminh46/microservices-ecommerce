package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.entity.Warehouse;

import java.util.Optional;

public interface WarehouseRepository {
    Optional<Warehouse> save(Warehouse warehouse);
}
