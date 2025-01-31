package com.tuber.identity.service.dataaccess.role.entity;

import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
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
@Table(name = "role")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleJpaEntity {
    @Id
    @Column(name = "name", unique = true, nullable = false)
    String name;
    String description;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<UserAccountJpaEntity> users = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<PermissionJpaEntity> permissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleJpaEntity that = (RoleJpaEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
