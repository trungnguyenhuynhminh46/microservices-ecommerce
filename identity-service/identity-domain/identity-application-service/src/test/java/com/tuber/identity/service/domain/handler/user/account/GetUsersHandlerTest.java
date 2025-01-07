package com.tuber.identity.service.domain.handler.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.GetUsersResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUsersHandlerTest {
    @InjectMocks
    private GetUsersHandler getUsersHandler;
    @Mock
    private UserDataMapper userDataMapper;
    @Mock
    private UserAccountRepository userAccountRepository;

    private GetUsersResponseData getUsersResponseData;

    @BeforeEach
    void initData() {
        List<UserAccount> userAccounts = List.of(
                UserAccount.builder()
                        .id(UUID.randomUUID())
                        .username("Nguyen Van A")
                        .email("anguyen@gmail.com")
                        .password("anguyen@password")
                        .build(),
                UserAccount.builder()
                        .id(UUID.randomUUID())
                        .username("Nguyen Van B")
                        .email("bnguyen@gmail.com")
                        .password("bnguyen@password")
                        .build()
        );

        when(userAccountRepository.findAll()).thenReturn(userAccounts);
        getUsersResponseData = GetUsersResponseData.builder()
                .users(userAccounts.stream().map(userDataMapper::userAccountEntityToGetUserByIdResponseData).toList())
                .build();
        when(userDataMapper.userAccountEntitiesToGetUsersResponseData(userAccounts)).thenReturn(getUsersResponseData);
    }

    @Test
    void testGetUsers() {
        ApiResponse<GetUsersResponseData> response = getUsersHandler.getUsers();
        assertEquals(response.getCode(), IdentityResponseCode.SUCCESS_RESPONSE.getCode());
        assertEquals(response.getMessage(), "User accounts found");
        assertEquals(response.getData(), getUsersResponseData);
    }
}
