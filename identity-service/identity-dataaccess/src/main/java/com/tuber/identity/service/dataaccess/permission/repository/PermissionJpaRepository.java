package com.tuber.identity.service.dataaccess.permission.repository;

import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, String> {
    Optional<PermissionJpaEntity> findByName(String name);

    @Query("SELECT p FROM PermissionJpaEntity p JOIN p.roles r WHERE r.name = :roleName")
    Set<PermissionJpaEntity> findByRoleName(String roleName);

    @Query("""
            SELECT p
             FROM PermissionJpaEntity p
             JOIN p.roles r
             JOIN r.users u
             WHERE u.username = :username
            """)
    Set<PermissionJpaEntity> findByUsername(String username);
}
