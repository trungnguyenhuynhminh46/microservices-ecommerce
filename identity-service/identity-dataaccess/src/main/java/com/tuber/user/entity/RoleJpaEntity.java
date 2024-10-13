package com.tuber.user.entity;

import com.tuber.domain.valueobject.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "role")
@Entity
@FieldDefaults(level= AccessLevel.PRIVATE)
public class RoleJpaEntity {
    @Id
    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    UserRole name;
    String description;
    @ManyToMany(mappedBy = "roles")
    Set<UserAccountJpaEntity> users;
    @ManyToMany
    @JoinTable(
            name = "role_permission",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<PermissionJpaEntity> permissions;

    public void setName(final UserRole name) {
        this.name = name;
    }

    public void setName(final String name) {
        this.name = UserRole.valueOf(name);
    }
}
