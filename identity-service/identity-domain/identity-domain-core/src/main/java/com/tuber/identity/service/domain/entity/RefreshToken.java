package com.tuber.identity.service.domain.entity;

import com.tuber.domain.entity.BaseEntity;
import com.tuber.identity.service.domain.valueobject.RefreshTokenId;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken extends BaseEntity<RefreshTokenId> {
    private final UUID userId;

    private final Instant expiresIn;

    private RefreshToken(Builder builder) {
        super.setId(builder.token);
        userId = builder.userId;
        expiresIn = builder.expiresIn;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getUserId() {
        return userId;
    }

    public Instant getExpiresIn() {
        return expiresIn;
    }

    public static final class Builder {
        private RefreshTokenId token;
        private UUID userId;

        private Instant expiresIn;

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

        public Builder expiresIn(Instant val) {
            expiresIn = val;
            return this;
        }

        public RefreshToken build() {
            return new RefreshToken(this);
        }
    }
}
