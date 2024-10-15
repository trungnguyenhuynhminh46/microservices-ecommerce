package com.tuber.identity.service.domain.handler;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.constant.IdentityResponseCode;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdQuery;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import com.tuber.identity.service.domain.entity.UserAccount;
import com.tuber.identity.service.domain.exception.UserAccountNotFoundException;
import com.tuber.identity.service.domain.mapper.UserDataMapper;
import com.tuber.identity.service.domain.ports.output.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetUserByIdHandler {
    private final UserAccountRepository userAccountRepository;
    private final UserDataMapper userDataMapper;

    private UserAccount checkIfUserAccountWithIdExists(UUID id) {
        Optional<UserAccount> userAccount = userAccountRepository.findById(id);
        if(userAccount.isEmpty()) {
            log.warn("Could not find user account with id: {}", id);
            throw new UserAccountNotFoundException(IdentityResponseCode.USER_ACCOUNT_WITH_ID_NOT_FOUND, HttpStatus.NOT_FOUND.value());
        }
        return userAccount.get();
    }

    public ApiResponse<GetUserByIdResponseData> getUserByUserId(GetUserByIdQuery getUserByIdQuery) {
        UserAccount userAccount = checkIfUserAccountWithIdExists(getUserByIdQuery.getUserId());
        GetUserByIdResponseData getUserByIdResponseData = userDataMapper.userAccountEntityToGetUserByIdResponseData(userAccount);

        return ApiResponse.<GetUserByIdResponseData>builder()
                .code(IdentityResponseCode.SUCCESS_RESPONSE.getCode())
                .message(String.format("User account with id %s found", getUserByIdQuery.getUserId()))
                .data(getUserByIdResponseData)
                .build();
    }
}
