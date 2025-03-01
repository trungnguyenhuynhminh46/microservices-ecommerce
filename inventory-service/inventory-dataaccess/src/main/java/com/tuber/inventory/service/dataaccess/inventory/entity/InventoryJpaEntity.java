package com.tuber.inventory.service.dataaccess.inventory.entity;

import com.tuber.inventory.service.dataaccess.warehouse.entity.WarehouseJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InventoryJpaEntity {
    @Id
    UUID id;
    UUID productId;
    String sku;
    @Column(name = "warehouse_id", nullable = false)
    UUID warehouseId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", insertable = false, updatable = false)
    WarehouseJpaEntity warehouse;
    Integer stockQuantity;
    String creator;
    String updater;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InventoryJpaEntity that = (InventoryJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
