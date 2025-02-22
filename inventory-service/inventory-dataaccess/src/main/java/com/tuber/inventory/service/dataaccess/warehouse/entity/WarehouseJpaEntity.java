package com.tuber.inventory.service.dataaccess.warehouse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "warehouse")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarehouseJpaEntity {
    @Id
    UUID id;
    String name;
    String postalCode;
    String street;
    String city;
    Double latitude;
    Double longitude;
    String description;
    @Column(columnDefinition = "DATE")
    String createdAt;
    @Column(columnDefinition = "DATE")
    String updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseJpaEntity that = (WarehouseJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
