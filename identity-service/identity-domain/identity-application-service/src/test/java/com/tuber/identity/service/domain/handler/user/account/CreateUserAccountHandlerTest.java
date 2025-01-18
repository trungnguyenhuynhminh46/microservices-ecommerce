package com.tuber.identity.service.domain.handler.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.http.client.profile.CreateUserProfileCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
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

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateUserAccountHandlerTest {
    @InjectMocks
    private CreateUserAccountHandler createUserAccountHandler;
    @Mock
    private CreateUserAccountHelper createUserAccountHelper;
    @Mock
    private UserDataMapper userDataMapper;
    @Mock
    private ProfileServiceClient profileServiceClient;
    @Mock
    private ProfileServiceDataMapper profileServiceDataMapper;
    private CreateUserAccountCommand createUserAccountCommand;
    private CreateUserProfileCommand createUserProfileCommand;
    private CreateUserAccountResponseData createUserAccountResponseData;

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
        createUserAccountCommand = CreateUserAccountCommand.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        createUserProfileCommand = CreateUserProfileCommand.builder()
                .userId(userAccount.getId().toString())
                .firstName(createUserAccountCommand.getFirstName())
                .lastName(createUserAccountCommand.getLastName())
                .dob(createUserAccountCommand.getDob())
                .city(createUserAccountCommand.getCity())
                .build();
        UserAccountCreatedEvent userAccountCreatedEvent = new UserAccountCreatedEvent(userAccount);
        createUserAccountResponseData = CreateUserAccountResponseData.builder()
                .id(userAccount.getId().getValue())
                .username(userAccount.getUsername())
                .email(userAccount.getEmail())
                .build();

        when(createUserAccountHelper.persistUserAccount(any())).thenReturn(userAccountCreatedEvent);
        when(userDataMapper.userAccountEntityToCreateUserAccountResponseData(userAccount)).thenReturn(createUserAccountResponseData);
        when(profileServiceDataMapper.createUserAccountCommandToCreateUserProfileCommand(createUserAccountCommand, userAccountCreatedEvent.getUserAccount().getUserId())).thenReturn(createUserProfileCommand);
    }

    @Test
    void createUserAccountSuccessfully() {
        ApiResponse<CreateUserAccountResponseData> response = createUserAccountHandler.createUserAccount(createUserAccountCommand);
        assertEquals(response.getCode(), IdentityResponseCode.SUCCESS_RESPONSE.getCode());
        assertEquals(response.getData(), createUserAccountResponseData);
        verify(profileServiceClient, times(1)).createUserProfile(createUserProfileCommand);
    }
}
