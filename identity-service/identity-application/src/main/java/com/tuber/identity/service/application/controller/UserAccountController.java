package com.tuber.identity.service.application.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.user.account.*;
import com.tuber.identity.service.domain.ports.input.service.IdentityApplicationService;
import com.tuber.identity.service.domain.validators.ValidUUID;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
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

    @GetMapping
    public ResponseEntity<ApiResponse<GetUsersResponseData>> getUsers() {
        ApiResponse<GetUsersResponseData> getUsersResponseData = identityApplicationService.getUsers();
        log.info("Returning all user accounts");
        return ResponseEntity.ok(getUsersResponseData);
    }
}
