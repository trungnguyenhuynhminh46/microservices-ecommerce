package com.tuber.identity.service.dataaccess.user.mapper;

import com.tuber.identity.service.dataaccess.user.entity.RefreshTokenJpaEntity;
import com.tuber.identity.service.domain.entity.RefreshToken;
import com.tuber.identity.service.domain.valueobject.RefreshTokenId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RefreshTokenDataMapper {
    @Mapping(target = "id", source = "refreshTokenJpaEntity.token")
    public abstract RefreshToken refreshTokenJpaEntityToRefreshTokenEntity(RefreshTokenJpaEntity refreshTokenJpaEntity);

    @Mapping(target = "token", expression = "java(refreshToken.getId().getValue())")
    @Mapping(target = "userAccount", ignore = true)
    public abstract RefreshTokenJpaEntity refreshTokenEntityToRefreshTokenJpaEntity(RefreshToken refreshToken);

    protected RefreshTokenId map(String token) {
        return new RefreshTokenId(token);
    }
}
