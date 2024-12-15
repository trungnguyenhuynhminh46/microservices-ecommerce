package com.tuber.identity.service.domain.helper.authentication;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.authentication.LogoutUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.LogoutUserAccountResponseData;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogoutUserAccountHelper {
    JwtTokenHelper jwtTokenHelper;

    public ApiResponse<LogoutUserAccountResponseData> logout(LogoutUserAccountCommand logoutUserAccountCommand) {
        String refreshToken = logoutUserAccountCommand.getRefreshToken();
        jwtTokenHelper.verifyRefreshToken(refreshToken);
        jwtTokenHelper.logout(refreshToken);

        return ApiResponse.<LogoutUserAccountResponseData>builder()
                .data(LogoutUserAccountResponseData.builder().success(true).build())
                .build();
    }
}
