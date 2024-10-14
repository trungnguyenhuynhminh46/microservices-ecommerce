package com.tuber.identity.service.dataaccess.role.repository;

import com.tuber.domain.valueobject.enums.UserRole;
import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, UserRole> {
}
