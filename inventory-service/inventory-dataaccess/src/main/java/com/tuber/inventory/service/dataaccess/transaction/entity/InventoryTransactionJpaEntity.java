package com.tuber.inventory.service.dataaccess.transaction.entity;

import com.tuber.domain.valueobject.enums.InventoryTransactionType;
import com.tuber.inventory.service.dataaccess.warehouse.entity.WarehouseJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_transaction")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryTransactionJpaEntity {
    @Id
    UUID id;
    UUID productId;
    String sku;

    @Column(name = "src_warehouse_id")
    UUID srcWarehouseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "src_warehouse_id", insertable = false, updatable = false)
    WarehouseJpaEntity srcWarehouse;

    @Column(name = "dest_warehouse_id", nullable = false)
    UUID destWarehouseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dest_warehouse_id", insertable = false, updatable = false)
    WarehouseJpaEntity destWarehouse;

    Integer quantity;
    UUID creatorId;
    @Enumerated(EnumType.STRING)
    InventoryTransactionType transactionType;
    String description;
    LocalDate createdDate;
}
