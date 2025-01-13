package com.tuber.profile.service.domain.controller.internal;

import com.tuber.application.handler.ApiResponse;
import com.tuber.profile.service.domain.dto.user.profile.CreateUserProfileCommand;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;
import com.tuber.profile.service.domain.ports.input.service.ProfileApplicationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/internal/profile", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InternalProfileController {
    ProfileApplicationService profileApplicationService;
    @PostMapping
    ResponseEntity<ApiResponse<UserProfileResponseData>> createUserProfile(@RequestBody @Valid CreateUserProfileCommand createUserProfileCommand) {
        log.info("Creating user profile for user with userId: {}", createUserProfileCommand.getUserId());
        ApiResponse<UserProfileResponseData> createUserProfileResponse = profileApplicationService.createUserProfile(createUserProfileCommand);
        log.info("User profile for userId {} has been created", createUserProfileCommand.getUserId());
        return ResponseEntity.ok(createUserProfileResponse);
    }
}
