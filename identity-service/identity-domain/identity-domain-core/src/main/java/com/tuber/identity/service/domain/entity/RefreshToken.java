package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.identity.service.domain.valueobject.RefreshTokenId;

import java.util.UUID;

public class RefreshToken extends BaseEntity<RefreshTokenId> {
    private final UUID userId;
    private Boolean isRevoked = false;

    private RefreshToken(Builder builder) {
        super.setId(builder.token);
        userId = builder.userId;
        isRevoked = builder.isRevoked;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getUserId() {
        return userId;
    }

    public boolean getIsRevoked() {
        return isRevoked;
    }


    public static final class Builder {
        private RefreshTokenId token;
        private UUID userId;
        private Boolean isRevoked = false;

        private Builder() {
        }

        public Builder id(RefreshTokenId token) {
            this.token = token;
            return this;
        }

        public Builder userId(UUID val) {
            userId = val;
            return this;
        }

        public Builder isRevoked(Boolean val) {
            isRevoked = val;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this);
        }
    }

    public void setIsRevoked(Boolean isRevoked) {
        this.isRevoked = isRevoked;
    }
}
