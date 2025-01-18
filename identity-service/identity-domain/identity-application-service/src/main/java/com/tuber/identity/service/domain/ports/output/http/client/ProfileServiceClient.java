package com.tuber.identity.service.domain.ports.output.http.client;

import com.tuber.application.handler.ApiResponse;
import com.tuber.identity.service.domain.dto.http.client.profile.CreateUserProfileCommand;
import com.tuber.identity.service.domain.dto.http.client.profile.UserProfileResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${profile-service.url}")
public interface ProfileServiceClient {
    @PostMapping(value = "/internal", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ApiResponse<UserProfileResponseData>> createUserProfile(@RequestBody CreateUserProfileCommand command);
}
