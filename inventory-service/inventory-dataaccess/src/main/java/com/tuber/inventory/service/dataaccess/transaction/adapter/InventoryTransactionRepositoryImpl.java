package com.tuber.inventory.service.dataaccess.transaction.adapter;

import com.tuber.inventory.service.dataaccess.transaction.mapper.InventoryTransactionJpaMapper;
import com.tuber.inventory.service.dataaccess.transaction.repository.InventoryTransactionJpaRepository;
import com.tuber.inventory.service.domain.entity.InventoryTransaction;
import com.tuber.inventory.service.domain.ports.output.repository.InventoryTransactionRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InventoryTransactionRepositoryImpl implements InventoryTransactionRepository {
    InventoryTransactionJpaRepository inventoryTransactionJpaRepository;
    InventoryTransactionJpaMapper inventoryTransactionMapper;

    @Override
    public InventoryTransaction save(InventoryTransaction inventoryTransaction) {
        return inventoryTransactionMapper.inventoryTransactionJpaEntityToInventoryTransactionEntity(
                inventoryTransactionJpaRepository.save(
                        inventoryTransactionMapper.inventoryTransactionEntityToInventoryTransactionJpaEntity(inventoryTransaction)
                )
        );
    }
}
