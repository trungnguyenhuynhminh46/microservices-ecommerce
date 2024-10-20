package com.tuber.identity.service.dataaccess.user.mapper;

import com.tuber.identity.service.dataaccess.user.entity.RefreshTokenJpaEntity;
import com.tuber.identity.service.domain.entity.RefreshToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class RefreshTokenDataMapper {
    @Mapping(target = "id", source = "refreshTokenJpaEntity.token")
    @Mapping(target = "userAccount", ignore = true)
    public abstract RefreshToken refreshTokenJpaEntityToRefreshTokenEntity(RefreshTokenJpaEntity refreshTokenJpaEntity);

    @Mapping(target = "token", expression = "refreshToken.getId().getValue()")
    @Mapping(target = "userAccount", ignore = true)
    public abstract RefreshTokenJpaEntity refreshTokenEntityToRefreshTokenJpaEntity(RefreshToken refreshToken);
}
