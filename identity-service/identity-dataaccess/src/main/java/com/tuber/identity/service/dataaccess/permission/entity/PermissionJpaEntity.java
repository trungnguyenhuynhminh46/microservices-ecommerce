package com.tuber.identity.service.dataaccess.permission.entity;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class PermissionJpaEntity {
    @Id
    @Enumerated(EnumType.STRING)
    UserPermission name;
    String description;
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    Set<RoleJpaEntity> roles = new HashSet<>();
}
