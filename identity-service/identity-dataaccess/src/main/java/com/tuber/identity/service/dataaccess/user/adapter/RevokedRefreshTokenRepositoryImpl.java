package com.tuber.identity.service.dataaccess.user.adapter;

import com.tuber.identity.service.dataaccess.user.mapper.RevokedRefreshTokenDataMapper;
import com.tuber.identity.service.dataaccess.user.repository.RefreshTokenJpaRepository;
import com.tuber.identity.service.domain.entity.RevokedRefreshToken;
import com.tuber.identity.service.domain.ports.output.repository.RevokedRefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RevokedRefreshTokenRepositoryImpl implements RevokedRefreshTokenRepository {
    RefreshTokenJpaRepository refreshTokenJpaRepository;
    RevokedRefreshTokenDataMapper revokedRefreshTokenDataMapper;

    @Override
    public RevokedRefreshToken save(RevokedRefreshToken revokedRefreshToken) {
        return revokedRefreshTokenDataMapper.refreshTokenJpaEntityToRefreshTokenEntity(
                refreshTokenJpaRepository.save(
                        revokedRefreshTokenDataMapper.refreshTokenEntityToRefreshTokenJpaEntity(revokedRefreshToken)
                )
        );
    }

    @Override
    public Optional<RevokedRefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token)
                .map(revokedRefreshTokenDataMapper::refreshTokenJpaEntityToRefreshTokenEntity);
    }
}
