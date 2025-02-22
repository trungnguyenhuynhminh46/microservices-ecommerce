package com.tuber.inventory.service.dataaccess.warehouse.repository;

import com.tuber.inventory.service.dataaccess.warehouse.entity.WarehouseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
}
