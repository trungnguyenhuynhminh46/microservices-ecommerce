package com.tuber.controller;

import com.tuber.application.handler.ResponseBase;
import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.dto.user.account.CreateUserAccountResponseData;
import com.tuber.ports.input.service.IdentityApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class UserAccountController {
    private final IdentityApplicationService identityApplicationService;
    @PostMapping
    public ResponseEntity<ResponseBase<CreateUserAccountResponseData>> createUserAccount(@RequestBody CreateUserAccountCommand createUserAccountCommand) {
        log.info("Creating user account with username: {} and email: {}", createUserAccountCommand.getUsername(), createUserAccountCommand.getEmail());
        ResponseBase<CreateUserAccountResponseData> createUserAccountResponse = identityApplicationService.createUserAccount(createUserAccountCommand);
        log.info("User account created with id: {} and username: {}", createUserAccountResponse.getData().getId(), createUserAccountResponse.getData().getUsername());
        return ResponseEntity.ok(createUserAccountResponse);
    }
}
