package com.tuber.identity.service.dataaccess.user.adapter;

import com.tuber.identity.service.dataaccess.user.mapper.RefreshTokenDataMapper;
import com.tuber.identity.service.dataaccess.user.repository.RefreshTokenJpaRepository;
import com.tuber.identity.service.domain.entity.RefreshToken;
import com.tuber.identity.service.domain.ports.output.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {
    RefreshTokenJpaRepository refreshTokenJpaRepository;
    RefreshTokenDataMapper refreshTokenDataMapper;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenDataMapper.refreshTokenJpaEntityToRefreshTokenEntity(
                refreshTokenJpaRepository.save(
                        refreshTokenDataMapper.refreshTokenEntityToRefreshTokenJpaEntity(refreshToken)
                )
        );
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token)
                .map(refreshTokenDataMapper::refreshTokenJpaEntityToRefreshTokenEntity);
    }

    @Override
    public List<RefreshToken> findByUserId(UUID userId) {
        return refreshTokenJpaRepository.findByUserId(userId)
                .stream()
                .map(refreshTokenDataMapper::refreshTokenJpaEntityToRefreshTokenEntity)
                .toList();
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenJpaRepository.deleteByToken(token);
    }
}
