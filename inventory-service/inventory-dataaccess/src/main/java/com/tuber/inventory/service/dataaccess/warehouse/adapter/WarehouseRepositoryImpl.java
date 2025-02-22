package com.tuber.inventory.service.dataaccess.warehouse.adapter;

import com.tuber.inventory.service.dataaccess.warehouse.mapper.WarehouseJpaMapper;
import com.tuber.inventory.service.dataaccess.warehouse.repository.WarehouseJpaRepository;
import com.tuber.inventory.service.domain.entity.Warehouse;
import com.tuber.inventory.service.domain.ports.output.repository.WarehouseRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WarehouseRepositoryImpl implements WarehouseRepository {
    WarehouseJpaMapper warehouseJpaMapper;
    WarehouseJpaRepository warehouseJpaRepository;

    @Override
    public Optional<Warehouse> save(Warehouse warehouse) {
        return Optional.of(warehouseJpaMapper.warehouseJpaEntityToWarehouseEntity(
                warehouseJpaRepository.save(
                        warehouseJpaMapper.warehouseEntityToWarehouseJpaEntity(warehouse)
                )
        ));
    }

    @Override
    public Boolean existsById(UUID id) {
        return null;
    }

    @Override
    public Optional<Warehouse> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<Warehouse> findAll() {
        return null;
    }
}
