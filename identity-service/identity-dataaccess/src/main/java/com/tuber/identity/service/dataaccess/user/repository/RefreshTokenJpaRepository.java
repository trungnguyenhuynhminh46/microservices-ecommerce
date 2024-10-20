package com.tuber.identity.service.dataaccess.user.repository;

import com.tuber.identity.service.dataaccess.user.entity.RefreshTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, UUID> {
    Optional<RefreshTokenJpaEntity> findByToken(String token);
    List<RefreshTokenJpaEntity> findByUserId(UUID userId);
    void deleteByToken(String token);
    boolean existsByToken(String token);
}
