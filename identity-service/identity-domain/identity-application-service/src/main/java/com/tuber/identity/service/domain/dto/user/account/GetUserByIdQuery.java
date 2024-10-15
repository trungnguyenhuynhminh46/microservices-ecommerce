package com.tuber.identity.service.domain.dto.user.account;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetUserByIdQuery {
    @NotNull
    UUID userId;

    public static GetUserByIdQueryBuilder builder() {
        return new GetUserByIdQueryBuilder();
    }

    public static class GetUserByIdQueryBuilder {
        private UUID userId;

        GetUserByIdQueryBuilder() {
        }

        public GetUserByIdQueryBuilder userId(final String userId) {
            this.userId = UUID.fromString(userId);
            return this;
        }

        @Generated
        public GetUserByIdQuery build() {
            return new GetUserByIdQuery(this.userId);
        }

        @Generated
        public String toString() {
            return "GetUserByIdQuery.GetUserByIdQueryBuilder(userId=" + String.valueOf(this.userId) + ")";
        }
    }
}
