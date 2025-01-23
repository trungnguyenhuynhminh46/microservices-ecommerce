package com.tuber.api_gateway.repository;

import com.tuber.api_gateway.dto.ApiResponse;
import com.tuber.api_gateway.dto.request.IntrospectUserAccountCommand;
import com.tuber.api_gateway.dto.response.IntrospectUserAccountResponseData;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

public interface IdentityClient {
    @PostExchange(url = "/auth/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ResponseEntity<ApiResponse<IntrospectUserAccountResponseData>>> introspectUserAccount(@RequestBody IntrospectUserAccountCommand introspectUserAccountCommand);
}
