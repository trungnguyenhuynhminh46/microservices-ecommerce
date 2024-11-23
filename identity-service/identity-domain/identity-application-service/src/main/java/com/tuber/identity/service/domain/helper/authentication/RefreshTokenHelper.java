package com.tuber.identity.service.domain.helper.authentication;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.RefreshTokenCommand;
import com.tuber.identity.service.domain.dto.authentication.RefreshTokenResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenHelper {
    JwtTokenHelper jwtTokenHelper;
    CommonIdentityServiceHelper commonIdentityServiceHelper;

    private String generateNewAccessToken(String refreshToken) {
        String username = jwtTokenHelper.extractSubjectFromToken(refreshToken);
        UserAccount userAccount = commonIdentityServiceHelper.verifyUserAccountWithUsernameExist(username);

        return jwtTokenHelper.generateJwtAccessToken(userAccount);
    }

    public ApiResponse<RefreshTokenResponseData> refresh(RefreshTokenCommand refreshTokenCommand) {
        String refreshToken = refreshTokenCommand.getRefreshToken();
        boolean isActive = jwtTokenHelper.verifyToken(refreshToken);

        if (isActive) {
            RefreshTokenResponseData refreshTokenResponseData = RefreshTokenResponseData.builder()
                    .accessToken(generateNewAccessToken(refreshToken))
                    .refreshToken(jwtTokenHelper.rotateRefreshToken(refreshToken))
                    .build();
            return ApiResponse.<RefreshTokenResponseData>builder()
                    .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                    .message("Token refreshed successfully!")
                    .data(refreshTokenResponseData)
                    .build();
        }

        return ApiResponse.<RefreshTokenResponseData>builder()
                .code(IdentityResponseCode.INVALID_JWT_TOKEN.getCode())
                .message("The token you input is invalid, please login again to continue!")
                .build();
    }
}
