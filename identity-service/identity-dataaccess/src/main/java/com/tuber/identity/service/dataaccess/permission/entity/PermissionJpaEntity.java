package com.tuber.identity.service.dataaccess.permission.entity;

import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PermissionJpaEntity {
    @Id
    @Column(name = "name", unique = true, nullable = false)
    String name;
    String description;
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    Set<RoleJpaEntity> roles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionJpaEntity that = (PermissionJpaEntity) o;
        return name == that.name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
