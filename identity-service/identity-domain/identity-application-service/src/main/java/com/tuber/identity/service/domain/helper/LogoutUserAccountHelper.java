package com.tuber.identity.service.domain.helper;

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

    private RefreshToken verifyUserNotLoggedOut(String refreshToken) {
        Optional<RefreshToken> savedRefreshToken = refreshTokenRepository.findByToken(refreshToken);
        boolean isLoggedOut = savedRefreshToken.isEmpty() || savedRefreshToken.get().isRevoked();
        if (isLoggedOut) {
            throw new LoggedOutAlready(IdentityResponseCode.LOGGED_OUT_ALREADY, HttpStatus.ALREADY_REPORTED.value());
        }
        return savedRefreshToken.get();
    }

    private void revokeUserAccount(RefreshToken refreshToken) {
        refreshToken.setIsRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

    public ApiResponse<LogoutUserAccountResponseData> logout(LogoutUserAccountCommand logoutUserAccountCommand) {
        RefreshToken refreshToken = verifyUserNotLoggedOut(logoutUserAccountCommand.getRefreshToken());
        revokeUserAccount(refreshToken);

        return ApiResponse.<LogoutUserAccountResponseData>builder()
                .data(LogoutUserAccountResponseData.builder().success(true).build())
                .build();
    }
}
