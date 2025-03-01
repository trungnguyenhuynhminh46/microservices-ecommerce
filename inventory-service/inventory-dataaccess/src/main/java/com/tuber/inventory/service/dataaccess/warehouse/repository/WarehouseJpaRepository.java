package com.tuber.inventory.service.dataaccess.warehouse.repository;

import com.tuber.inventory.service.dataaccess.warehouse.entity.WarehouseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseJpaRepository extends JpaRepository<WarehouseJpaEntity, UUID> {
}
