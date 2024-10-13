package com.tuber.user.entity;

import com.tuber.domain.valueobject.enums.UserPermission;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @Setter(AccessLevel.NONE)
    UserPermission name;
    String description;
    @ManyToMany(mappedBy = "permissions")
    Set<RoleJpaEntity> roles;

    public void setName(final UserPermission name) {
        this.name = name;
    }

    public void setName(final String name) {
        this.name = UserPermission.valueOf(name);
    }
}
