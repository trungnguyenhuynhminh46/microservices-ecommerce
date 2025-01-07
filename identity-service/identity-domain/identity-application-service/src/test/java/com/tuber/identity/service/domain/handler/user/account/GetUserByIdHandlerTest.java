package com.tuber.identity.service.domain.handler.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdQuery;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetUserByIdHandlerTest {
    @InjectMocks
    private GetUserByIdHandler getUserByIdHandler;
    @Mock
    private UserDataMapper userDataMapper;
    @Mock
    private CommonIdentityServiceHelper commonIdentityServiceHelper;
    private GetUserByIdQuery getUserByIdQuery;
    private GetUserByIdResponseData getUserByIdResponseData;

    @BeforeEach
    void initData() {
        UUID userId = UUID.randomUUID();
        String username = "Peter Nguyen";
        String email = "peternguyen123@gmail.com";
        String password = "peternguyen123@password";
        UserAccount userAccount = UserAccount.builder()
                .id(userId)
                .username(username)
                .email(email)
                .password(password)
                .build();
        getUserByIdQuery = new GetUserByIdQuery(userId);
        getUserByIdResponseData = GetUserByIdResponseData.builder()
                .id(userId)
                .username(username)
                .email(email)
                .build();

        when(commonIdentityServiceHelper.verifyUserAccountWithIdExist(userId)).thenReturn(userAccount);
        when(userDataMapper.userAccountEntityToGetUserByIdResponseData(userAccount)).thenReturn(getUserByIdResponseData);
    }

    @Test
    void testGetUserByUserId() {
        ApiResponse<GetUserByIdResponseData> response = getUserByIdHandler.getUserByUserId(getUserByIdQuery);
        assertEquals(response.getCode(), IdentityResponseCode.SUCCESS_RESPONSE.getCode());
        assertEquals(response.getMessage(), String.format("User account with id %s found", getUserByIdQuery.getUserId()));
        assertEquals(response.getData(), getUserByIdResponseData);
    }
}
