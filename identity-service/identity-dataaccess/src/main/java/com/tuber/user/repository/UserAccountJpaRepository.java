package com.tuber.user.repository;

import com.tuber.user.entity.UserAccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.Optional;

@Repository
public interface UserAccountJpaRepository extends JpaRepository<UserAccountJpaEntity, UUID> {
    Optional<UserAccountJpaEntity> findByUsername(String username);
    Optional<UserAccountJpaEntity> findByEmail(String email);
}
