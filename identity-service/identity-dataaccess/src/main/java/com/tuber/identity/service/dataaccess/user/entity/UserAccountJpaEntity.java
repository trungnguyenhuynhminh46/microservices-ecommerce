package com.tuber.identity.service.dataaccess.user.entity;

import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_account")
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAccountJpaEntity {
    @Id
    UUID id;
    String username;
    String email;
    String password;
    @ManyToMany
    @JoinTable(
            name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleJpaEntity> roles;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;
}
