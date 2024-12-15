package com.tuber.identity.service.domain.ports.output.repository;

import com.tuber.identity.service.domain.entity.RevokedRefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RevokedRefreshTokenRepository {
    RevokedRefreshToken save(RevokedRefreshToken revokedRefreshToken);
    Optional<RevokedRefreshToken> findByToken(String token);
}
