package com.tuber.api_gateway.service;

import com.tuber.api_gateway.dto.ApiResponse;
import com.tuber.api_gateway.dto.request.IntrospectUserAccountCommand;
import com.tuber.api_gateway.dto.response.IntrospectUserAccountResponseData;
import com.tuber.api_gateway.repository.IdentityClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    IdentityClient identityClient;
    public Mono<ResponseEntity<ApiResponse<IntrospectUserAccountResponseData>>> introspect(String token) {
        IntrospectUserAccountCommand command = IntrospectUserAccountCommand.builder().accessToken(token).build();
        return identityClient.introspectUserAccount(command);
    }
}
