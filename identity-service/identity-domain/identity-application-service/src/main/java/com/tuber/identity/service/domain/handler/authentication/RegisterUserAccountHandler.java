package com.tuber.identity.service.domain.handler.authentication;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.helper.authentication.JwtTokenHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.mapper.http.client.ProfileServiceDataMapper;
import com.tuber.identity.service.domain.ports.output.http.client.ProfileServiceClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegisterUserAccountHandler {
    CreateUserAccountHelper createUserAccountHelper;
    JwtTokenHelper jwtTokenHelper;
    UserDataMapper userDataMapper;
    ProfileServiceClient profileServiceClient;
    ProfileServiceDataMapper profileServiceDataMapper;

    private RegisterUserAccountResponseData buildResponseData(UserAccount userAccount) {
        String accessToken = jwtTokenHelper.generateJwtAccessToken(userAccount);
        String refreshToken = jwtTokenHelper.generateJwtRefreshToken(userAccount);
        return RegisterUserAccountResponseData.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ApiResponse<RegisterUserAccountResponseData> register(RegisterUserAccountCommand registerUserAccountCommand) {
        UserAccountCreatedEvent userAccountCreatedEvent = createUserAccountHelper.persistUserAccount(userDataMapper.registerUserAccountCommandToCreateUserAccountCommand(registerUserAccountCommand));

        profileServiceClient.createProfile(profileServiceDataMapper.registerUserAccountCommandToCreateUserProfileCommand(registerUserAccountCommand, userAccountCreatedEvent.getUserAccount().getUserId()));

        log.info("User account is created with id: {}", userAccountCreatedEvent.getUserAccount().getUserId());
        UserAccount userAccount = userAccountCreatedEvent.getUserAccount();

        // Persist to outbox table if needed

        log.info("Returning CreateUserAccountResponseData with user account id: {}", userAccountCreatedEvent.getUserAccount().getUserId());
        return ApiResponse.<RegisterUserAccountResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message(String.format("User account created successfully with id: %s", userAccount.getUserId()))
                .data(buildResponseData(userAccount))
                .build();
    }
}
