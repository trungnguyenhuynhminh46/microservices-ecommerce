package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findByUserId(UUID userId);
    void deleteByToken(String token);
    boolean existsByToken(String token);
    boolean existsByTokenAndIsRevoked(String token, boolean isRevoked);
}
