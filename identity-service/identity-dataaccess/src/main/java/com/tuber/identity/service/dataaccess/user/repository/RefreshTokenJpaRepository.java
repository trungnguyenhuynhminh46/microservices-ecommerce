package com.tuber.identity.service.dataaccess.user.repository;

import com.tuber.identity.service.dataaccess.user.entity.RevokedRefreshTokenJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenJpaRepository extends JpaRepository<RevokedRefreshTokenJpaEntity, UUID> {
    Optional<RevokedRefreshTokenJpaEntity> findByToken(String token);
    List<RevokedRefreshTokenJpaEntity> findByUserId(UUID userId);
    void deleteByToken(String token);
}
