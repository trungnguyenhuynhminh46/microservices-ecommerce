package com.tuber.identity.service.dataaccess.user.entity;

import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
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
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_account_role",
            joinColumns = @JoinColumn(name = "user_account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleJpaEntity> roles;
    @Column(columnDefinition = "DATE")
    LocalDate createdAt;
    @Column(columnDefinition = "DATE")
    LocalDate updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccountJpaEntity that = (UserAccountJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
