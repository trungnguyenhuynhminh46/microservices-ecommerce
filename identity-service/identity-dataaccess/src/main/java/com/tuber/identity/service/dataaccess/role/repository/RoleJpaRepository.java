package com.tuber.identity.service.dataaccess.role.repository;

import com.tuber.identity.service.dataaccess.role.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleJpaEntity, String> {
    Optional<RoleJpaEntity> findByName(String name);

    @Query("SELECT r FROM RoleJpaEntity r JOIN r.users u WHERE u.username = :username")
    Set<RoleJpaEntity> findByUsername(String username);
}
