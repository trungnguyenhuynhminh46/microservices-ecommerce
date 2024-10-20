package com.tuber.identity.service.domain.handler.user.account;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdQuery;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import com.tuber.identity.service.domain.dto.user.account.GetUsersResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.UserAccountNotFoundException;
import com.tuber.identity.service.domain.helper.CommonIdentityServiceHelper;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GetUserByIdHandler {
    UserAccountRepository userAccountRepository;
    UserDataMapper userDataMapper;
    CommonIdentityServiceHelper commonIdentityServiceHelper;

    public ApiResponse<GetUserByIdResponseData> getUserByUserId(GetUserByIdQuery getUserByIdQuery) {
        UserAccount userAccount = commonIdentityServiceHelper.verifyUserAccountWithIdExist(getUserByIdQuery.getUserId());
        GetUserByIdResponseData getUserByIdResponseData = userDataMapper.userAccountEntityToGetUserByIdResponseData(userAccount);

        return ApiResponse.<GetUserByIdResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message(String.format("User account with id %s found", getUserByIdQuery.getUserId()))
                .data(getUserByIdResponseData)
                .build();
    }
}
