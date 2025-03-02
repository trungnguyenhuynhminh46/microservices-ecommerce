package com.tuber.inventory.service.dataaccess.transaction.adapter;

import com.tuber.inventory.service.dataaccess.transaction.mapper.InventoryTransactionJpaMapper;
import com.tuber.inventory.service.dataaccess.transaction.repository.InventoryTransactionJpaRepository;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryTransactionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryTransactionRepositoryImpl implements InventoryTransactionRepository {
    InventoryTransactionJpaRepository inventoryTransactionJpaRepository;
    InventoryTransactionJpaMapper inventoryTransactionJpaMapper;

    @Override
    public InventoryTransaction save(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionJpaMapper.inventoryTransactionJpaEntityToInventoryTransactionEntity(
                inventoryTransactionJpaRepository.save(
                        inventoryTransactionJpaMapper.inventoryTransactionEntityToInventoryTransactionJpaEntity(inventoryTransaction)
                )
        );
    }

    @Override
    public List<InventoryTransaction> findAll() {
        return inventoryTransactionJpaRepository.findAll().stream().map(inventoryTransactionJpaMapper::inventoryTransactionJpaEntityToInventoryTransactionEntity).toList();
    }
}
