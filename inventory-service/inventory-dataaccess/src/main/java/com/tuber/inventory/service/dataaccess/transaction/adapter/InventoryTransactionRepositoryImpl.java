package com.tuber.inventory.service.dataaccess.transaction.adapter;

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
    @Override
    public InventoryTransaction save(InventoryTransaction inventoryTransaction) {
        return null;
    }
}
