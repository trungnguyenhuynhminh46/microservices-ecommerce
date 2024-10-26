package com.tuber.identity.service.dataaccess.permission.repository;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.identity.service.dataaccess.permission.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, UserPermission> {
    Optional<PermissionJpaEntity> findByName(UserPermission name);
}
