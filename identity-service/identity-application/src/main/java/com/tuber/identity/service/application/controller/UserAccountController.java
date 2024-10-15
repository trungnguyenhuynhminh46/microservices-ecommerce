package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountCommand;
import com.tuber.identity.service.domain.dto.user.account.CreateUserAccountResponseData;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdQuery;
import com.tuber.identity.service.domain.dto.user.account.GetUserByIdResponseData;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import com.tuber.identity.service.domain.validators.ValidUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class UserAccountController {
    private final IdentityApplicationService identityApplicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateUserAccountResponseData>> createUserAccount(@RequestBody @Valid CreateUserAccountCommand createUserAccountCommand) {
        log.info("Creating user account with username: {} and email: {}", createUserAccountCommand.getUsername(), createUserAccountCommand.getEmail());
        ApiResponse<CreateUserAccountResponseData> createUserAccountResponse = identityApplicationService.createUserAccount(createUserAccountCommand);
        log.info("User account created with id: {} and username: {}", createUserAccountResponse.getData().getId(), createUserAccountResponse.getData().getUsername());
        return ResponseEntity.ok(createUserAccountResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<GetUserByIdResponseData>> getUserByUserId(@ValidUUID @PathVariable("userId") String userId) {
        ApiResponse<GetUserByIdResponseData> getUserByIdResponseData = identityApplicationService.getUserByUserId(GetUserByIdQuery.builder().userId(userId).build());
        log.info("Returning user account with user id: {}", userId);
        return ResponseEntity.ok(getUserByIdResponseData);
    }
}
