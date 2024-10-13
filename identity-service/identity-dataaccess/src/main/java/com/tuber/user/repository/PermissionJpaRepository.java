package com.tuber.user.repository;

import com.tuber.domain.valueobject.enums.UserPermission;
import com.tuber.user.entity.PermissionJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionJpaEntity, UserPermission> {
}
