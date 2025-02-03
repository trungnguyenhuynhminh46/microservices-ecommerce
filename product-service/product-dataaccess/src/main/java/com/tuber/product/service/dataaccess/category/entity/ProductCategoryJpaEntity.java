package com.tuber.product.service.dataaccess.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCategoryJpaEntity {
    @Id
    UUID id;
    @Column(name = "code", unique = true, nullable = false)
    String code;
    @Column(name = "name", nullable = false)
    String name;
    String description;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;
}
