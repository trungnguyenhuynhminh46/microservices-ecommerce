package com.tuber.identity.service.dataaccess.user.mapper;

import com.tuber.identity.service.dataaccess.user.entity.RevokedRefreshTokenJpaEntity;
import com.tuber.identity.service.domain.entity.RevokedRefreshToken;
import com.tuber.identity.service.domain.valueobject.RefreshTokenId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RevokedRefreshTokenDataMapper {
    @Mapping(target = "id", source = "token")
    public abstract RevokedRefreshToken refreshTokenJpaEntityToRefreshTokenEntity(RevokedRefreshTokenJpaEntity revokedRefreshTokenJpaEntity);

    @Mapping(target = "token", source = "id")
    @Mapping(target = "userAccount", ignore = true)
    public abstract RevokedRefreshTokenJpaEntity refreshTokenEntityToRefreshTokenJpaEntity(RevokedRefreshToken revokedRefreshToken);

    protected RefreshTokenId map(String token) {
        return new RefreshTokenId(token);
    }

    protected String map(RefreshTokenId refreshTokenId) {
        return refreshTokenId.getValue();
    }
}
