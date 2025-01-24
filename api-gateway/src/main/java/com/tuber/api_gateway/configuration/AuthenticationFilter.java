package com.tuber.api_gateway.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuber.api_gateway.dto.ApiResponse;
import com.tuber.api_gateway.dto.ResponseCode;
import com.tuber.api_gateway.service.IdentityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationFilter implements GlobalFilter, Ordered {
    IdentityService identityService;
    ObjectMapper objectMapper;

    private Mono<Void> handleIntrospectionResponse(ServerWebExchange exchange, GatewayFilterChain chain, String token) {
        return identityService.introspect(token).flatMap(introspectResponse -> {
            if (introspectResponse.getBody().getData().isActive()) {
                return chain.filter(exchange);
            }
            return unauthenticated(exchange.getResponse());
        });
    }

    private String getAuthorizationToken(ServerWebExchange exchange) {
        List<String> authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION);
        if (CollectionUtils.isEmpty(authHeader)) {
            log.error("No authorization header found");
            return null;
        }
        String token = authHeader.getFirst().replace("Bearer ", "");
        log.info("Authentication token: {}", token);
        return token;
    }

    private ApiResponse<Object> createUnauthenticatedApiResponse() {
        return ApiResponse.builder()
                .code(ResponseCode.UNAUTHENTICATED.getCode())
                .message(ResponseCode.UNAUTHENTICATED.getMessage())
                .build();
    }

    private String convertApiResponseToJson(ApiResponse<Object> apiResponse) {
        try {
            return objectMapper.writeValueAsString(apiResponse);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("AuthenticationFilter was called");
        String token = getAuthorizationToken(exchange);
        if(token == null) {
            return unauthenticated(exchange.getResponse());
        }
        return handleIntrospectionResponse(exchange, chain, token);
    }

    @Override
    public int getOrder() {
        return -1;
    }

    Mono<Void> unauthenticated(ServerHttpResponse response) {
        ApiResponse<Object> apiResponse = createUnauthenticatedApiResponse();
        String body = convertApiResponseToJson(apiResponse);

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
