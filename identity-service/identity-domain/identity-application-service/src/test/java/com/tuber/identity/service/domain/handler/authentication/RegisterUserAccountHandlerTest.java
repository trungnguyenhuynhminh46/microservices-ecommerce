package com.tuber.identity.service.domain.handler.authentication;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountCommand;
import com.tuber.identity.service.domain.dto.authentication.RegisterUserAccountResponseData;
import com.tuber.identity.service.domain.dto.http.client.profile.CreateUserProfileCommand;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.authentication.JwtTokenHelper;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.mapper.http.client.ProfileServiceDataMapper;
import com.tuber.identity.service.domain.ports.output.http.client.ProfileServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @Mock
    private ProfileServiceClient profileServiceClient;
    @Mock
    private ProfileServiceDataMapper profileServiceDataMapper;

    private RegisterUserAccountCommand registerUserAccountCommand;
    private CreateUserProfileCommand createUserProfileCommand;

    @BeforeEach
    void initData() {
        String username = "Peter Nguyen";
        String email = "peternguyen123@gmail.com";
        String password = "peternguyen123@password";
        UserAccount userAccount = UserAccount.builder()
                .id(UUID.randomUUID())
                .username(username)
                .email(email)
                .password(password)
                .build();
        registerUserAccountCommand = RegisterUserAccountCommand.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        createUserProfileCommand = CreateUserProfileCommand.builder()
                .userId(userAccount.getId().toString())
                .firstName(registerUserAccountCommand.getFirstName())
                .lastName(registerUserAccountCommand.getLastName())
                .dob(registerUserAccountCommand.getDob())
                .city(registerUserAccountCommand.getCity())
                .build();
        UserAccountCreatedEvent userAccountCreatedEvent = new UserAccountCreatedEvent(userAccount);

        when(createUserAccountHelper.persistUserAccount(any())).thenReturn(new UserAccountCreatedEvent(userAccount));
        when(jwtTokenHelper.generateJwtAccessToken(userAccount)).thenReturn("access-token");
        when(jwtTokenHelper.generateJwtRefreshToken(userAccount)).thenReturn("refresh-token");
        when(profileServiceDataMapper.registerUserAccountCommandToCreateUserProfileCommand(registerUserAccountCommand, userAccountCreatedEvent.getUserAccount().getUserId()))
                .thenReturn(createUserProfileCommand);
    }

    @Test
    void registerUserAccountSuccessfully() {
        ApiResponse<RegisterUserAccountResponseData> response = registerUserAccountHandler.register(registerUserAccountCommand);
        assertEquals(response.getCode(), IdentityResponseCode.SUCCESS_RESPONSE.getCode());
        assertEquals(response.getData().getAccessToken(), "access-token");
        assertEquals(response.getData().getRefreshToken(), "refresh-token");
        verify(profileServiceClient, times(1)).createUserProfile(createUserProfileCommand);
    }
}
