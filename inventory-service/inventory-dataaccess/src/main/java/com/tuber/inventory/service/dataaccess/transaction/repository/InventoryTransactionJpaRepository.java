package com.tuber.inventory.service.dataaccess.transaction.repository;

import com.tuber.inventory.service.dataaccess.transaction.entity.InventoryTransactionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InventoryTransactionJpaRepository extends JpaRepository<InventoryTransactionJpaEntity, UUID> {
}
