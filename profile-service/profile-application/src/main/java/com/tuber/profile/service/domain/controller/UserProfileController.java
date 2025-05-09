package com.tuber.profile.service.domain.controller;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.validators.ValidUUID;
import com.tuber.profile.service.domain.dto.user.profile.QueryUserProfilesResponseData;
import com.tuber.profile.service.domain.dto.user.profile.UserProfileResponseData;
import com.tuber.profile.service.domain.ports.input.service.ProfileApplicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(value= "/${service.name}/users", produces = "application/vnd.api.v1+json")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserProfileController {
    ProfileApplicationService profileApplicationService;
    @GetMapping("/{profileId}")
    ResponseEntity<ApiResponse<UserProfileResponseData>> getProfile(@PathVariable @ValidUUID String profileId) {
        log.info("Getting profile with id: {}", profileId);
        ApiResponse<UserProfileResponseData> response = profileApplicationService.getProfile(profileId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    ResponseEntity<ApiResponse<QueryUserProfilesResponseData>> getUserProfiles() {
        log.info("Getting all user profiles");
        ApiResponse<QueryUserProfilesResponseData> response = profileApplicationService.getAllUserProfiles();
        return ResponseEntity.ok(response);
    }
}
