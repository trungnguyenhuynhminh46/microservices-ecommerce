package com.tuber.identity.service.dataaccess.role.entity;

import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleJpaEntity {
    @Id
    @Enumerated(EnumType.STRING)
    UserRole name;
    String description;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    Set<UserAccountJpaEntity> users;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<PermissionJpaEntity> permissions = new HashSet<>();
}
