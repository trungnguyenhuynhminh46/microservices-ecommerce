package com.tuber.inventory.service.domain.ports.output.repository;

import com.tuber.inventory.service.domain.entity.Warehouse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WarehouseRepository {
    Optional<Warehouse> save(Warehouse warehouse);
    Boolean existsById(UUID id);
    Optional<Warehouse> findById(UUID id);
    List<Warehouse> findAll();
}
