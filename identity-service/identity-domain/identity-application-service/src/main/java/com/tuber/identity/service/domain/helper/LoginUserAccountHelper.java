package com.tuber.identity.service.domain.helper;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.LoginUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.LoginUserAccountResponseData;
import com.tuber.identity.service.domain.entity.RefreshToken;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.valueobject.RefreshTokenId;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LoginUserAccountHelper {
    CommonIdentityServiceHelper commonIdentityServiceHelper;
    JwtTokenHelper jwtTokenHelper;

    private UserAccount verifyUserAccount(LoginUserAccountCommand loginUserAccountCommand) {
        String username = loginUserAccountCommand.getUsername();
        String password = loginUserAccountCommand.getPassword();
        UserAccount userAccount = commonIdentityServiceHelper.verifyUserAccountWithUsernameExist(username);
        commonIdentityServiceHelper.verifyPassword(password, userAccount.getPassword());

        return userAccount;
    }

    private LoginUserAccountResponseData buildUserAccountResponseData(UserAccount userAccount) {
        String accessToken = jwtTokenHelper.generateJwtAccessToken(userAccount);
        String refreshToken = jwtTokenHelper.generateJwtRefreshToken(userAccount);
        jwtTokenHelper.persistRefreshToken(RefreshToken.builder()
                .id(new RefreshTokenId(refreshToken))
                .userId(userAccount.getId().getValue())
                .isRevoked(false)
                .build());
        return LoginUserAccountResponseData.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ApiResponse<LoginUserAccountResponseData> login(LoginUserAccountCommand loginUserAccountCommand) {
        UserAccount userAccount = verifyUserAccount(loginUserAccountCommand);
        return ApiResponse.<LoginUserAccountResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message(String.format("User with username %s is successfully logged in", userAccount.getUsername()))
                .data(buildUserAccountResponseData(userAccount))
                .build();
    }
}
