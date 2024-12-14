package com.tuber.identity.service.dataaccess.user.repository;

import com.tuber.identity.service.dataaccess.user.entity.UserAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserAccountJpaRepository extends JpaRepository<UserAccountJpaEntity, UUID> {
    Optional<UserAccountJpaEntity> findByUsername(String username);
    Optional<UserAccountJpaEntity> findByEmail(String email);
    Boolean existsByUsername(String username);

    @Query("SELECT u FROM UserAccountJpaEntity u JOIN u.roles r WHERE r.name = :roleName")
    Set<UserAccountJpaEntity> findByRoleName(String roleName);

}
