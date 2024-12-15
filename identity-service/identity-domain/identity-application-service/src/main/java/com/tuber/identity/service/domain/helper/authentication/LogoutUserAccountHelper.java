package com.tuber.identity.service.domain.helper.authentication;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.LogoutUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.LogoutUserAccountResponseData;
import com.tuber.identity.service.domain.entity.RefreshToken;
import com.tuber.identity.service.domain.exception.LoggedOutAlready;
import com.tuber.identity.service.domain.ports.output.repository.RefreshTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutUserAccountHelper {
    RefreshTokenRepository refreshTokenRepository;
    AuthenticationHelper authenticationHelper;
    JwtTokenHelper jwtTokenHelper;

    private void verifyRefreshToken(String refreshToken) {
        String accessToken = authenticationHelper.getAccessToken();
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByToken(refreshToken);

        if (savedRefreshToken.isPresent()) {
            throw new LoggedOutAlready(IdentityResponseCode.LOGGED_OUT_ALREADY, HttpStatus.ALREADY_REPORTED.value());
        }
        jwtTokenHelper.verifyAccessTokenAndRefreshTokenHaveSameCreator(accessToken, refreshToken);
    }

    public ApiResponse<LogoutUserAccountResponseData> logout(LogoutUserAccountCommand logoutUserAccountCommand) {
        String refreshToken = logoutUserAccountCommand.getRefreshToken();
        verifyRefreshToken(refreshToken);
        jwtTokenHelper.logout(refreshToken);

        return ApiResponse.<LogoutUserAccountResponseData>builder()
                .data(LogoutUserAccountResponseData.builder().success(true).build())
                .build();
    }
}
