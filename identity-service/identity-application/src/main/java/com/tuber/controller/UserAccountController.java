package com.tuber.controller;

import com.tuber.application.handler.ResponseBase;
import com.tuber.dto.user.account.CreateUserAccountCommand;
import com.tuber.dto.user.account.CreateUserAccountResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/users", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
public class UserAccountController {
    @PostMapping
    public ResponseEntity<ResponseBase<CreateUserAccountResponseData>> createUserAccount(@RequestBody CreateUserAccountCommand createUserAccountCommand) {
        CreateUserAccountResponseData createUserAccountResponseData = CreateUserAccountResponseData.builder()
                .id("2")
                .username("johndoe")
                .email("abc.com")
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        ResponseBase<CreateUserAccountResponseData> response = ResponseBase.<CreateUserAccountResponseData>builder()
                .code("201")
                .message("User account created successfully")
                .data(createUserAccountResponseData)
                .build();
        return ResponseEntity.ok(response);
    }
}
