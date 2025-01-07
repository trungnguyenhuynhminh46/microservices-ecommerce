package com.tuber.identity.service.domain.handler.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.event.UserAccountCreatedEvent;
import com.tuber.identity.service.domain.helper.user.account.CreateUserAccountHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CreateUserAccountHandlerTest {
    @InjectMocks
    private CreateUserAccountHandler createUserAccountHandler;
    @Mock
    private CreateUserAccountHelper createUserAccountHelper;
    @Mock
    private UserDataMapper userDataMapper;
    private CreateUserAccountCommand createUserAccountCommand;
    private UserAccountCreatedEvent userAccountCreatedEvent;
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
        userAccountCreatedEvent = new UserAccountCreatedEvent(userAccount);
        createUserAccountResponseData = CreateUserAccountResponseData.builder()
                .id(userAccount.getId().getValue())
                .username(userAccount.getUsername())
                .email(userAccount.getEmail())
                .build();

        when(createUserAccountHelper.persistUserAccount(any())).thenReturn(userAccountCreatedEvent);
        when(userDataMapper.userAccountEntityToCreateUserAccountResponseData(userAccount)).thenReturn(createUserAccountResponseData);
    }

    @Test
    void createUserAccountSuccessfully() {
        ApiResponse<CreateUserAccountResponseData> response = createUserAccountHandler.createUserAccount(createUserAccountCommand);
        assertEquals(response.getCode(), IdentityResponseCode.SUCCESS_RESPONSE.getCode());
        assertEquals(response.getData(), createUserAccountResponseData);
    }
}
