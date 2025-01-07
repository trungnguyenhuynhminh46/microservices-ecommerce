package com.tuber.identity.service.domain.handler.authentication;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.authentication.JwtTokenHelper;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegisterUserAccountHandlerTest {
    @InjectMocks
    private RegisterUserAccountHandler registerUserAccountHandler;
    @Mock
    private CreateUserAccountHelper createUserAccountHelper;
    @Mock
    private JwtTokenHelper jwtTokenHelper;
    @Mock
    private UserDataMapper userDataMapper;
    private RegisterUserAccountCommand registerUserAccountCommand;

    @BeforeEach
    void initData() {
        String username = "Peter Nguyen";
        String email = "peternguyen123@gmail.com";
        String password = "peternguyen123@password";
        registerUserAccountCommand = RegisterUserAccountCommand.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        UserAccount userAccount = UserAccount.builder()
                .id(UUID.randomUUID())
                .username(username)
                .email(email)
                .password(password)
                .build();

        when(createUserAccountHelper.persistUserAccount(any())).thenReturn(new UserAccountCreatedEvent(userAccount));
        when(jwtTokenHelper.generateJwtAccessToken(userAccount)).thenReturn("access-token");
        when(jwtTokenHelper.generateJwtRefreshToken(userAccount)).thenReturn("refresh-token");
    }

    @Test
    void registerUserAccountSuccessfully() {
        ApiResponse<RegisterUserAccountResponseData> response = registerUserAccountHandler.register(registerUserAccountCommand);
        assertEquals(response.getCode(), IdentityResponseCode.SUCCESS_RESPONSE.getCode());
        assertEquals(response.getData().getAccessToken(), "access-token");
        assertEquals(response.getData().getRefreshToken(), "refresh-token");
    }
}
