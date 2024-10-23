package com.tuber.identity.service.domain.dto.authentication;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenResponseData {
    String accessToken;
    String refreshToken;
}
