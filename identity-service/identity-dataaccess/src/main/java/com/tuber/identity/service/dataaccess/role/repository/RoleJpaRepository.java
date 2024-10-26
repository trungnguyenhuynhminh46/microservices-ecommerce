package com.tuber.identity.service.dataaccess.role.repository;

import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, UserRole> {
    Optional<RoleJpaEntity> findByName(UserRole name);
}
