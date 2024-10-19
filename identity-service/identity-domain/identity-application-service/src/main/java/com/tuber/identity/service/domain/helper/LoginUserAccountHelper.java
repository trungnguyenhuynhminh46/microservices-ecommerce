package com.tuber.identity.service.domain.helper;

import com.nimbusds.jose.JOSEException;
import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.LoginUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.LoginUserAccountResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
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

    public ApiResponse<LoginUserAccountResponseData> login(LoginUserAccountCommand loginUserAccountCommand) {
        String username = loginUserAccountCommand.getUsername();
        String password = loginUserAccountCommand.getPassword();

        UserAccount userAccount = commonIdentityServiceHelper.verifyUserAccountExist(username);
        commonIdentityServiceHelper.verifyPassword(password, userAccount.getPassword());

        String token = jwtTokenHelper.generateJwtToken(userAccount);
        LoginUserAccountResponseData loginUserAccountResponseData = new LoginUserAccountResponseData(token);
        return ApiResponse.<LoginUserAccountResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message(String.format("User with username %s is successfully logged in", username))
                .data(loginUserAccountResponseData)
                .build();
    }
}
