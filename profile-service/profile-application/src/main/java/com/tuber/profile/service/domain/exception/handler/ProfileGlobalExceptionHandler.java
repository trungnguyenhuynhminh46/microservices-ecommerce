package com.tuber.profile.service.domain.exception.handler;

import com.tuber.application.handler.ApiResponse;
import com.tuber.application.handler.GlobalExceptionHandler;
import com.tuber.profile.service.domain.exception.ProfileDomainException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ProfileGlobalExceptionHandler extends GlobalExceptionHandler {
    @ExceptionHandler(value = ProfileDomainException.class)
    ResponseEntity<ApiResponse<Object>> handleProfileDomainException(ProfileDomainException identityDomainException) {
        log.error(identityDomainException.getMessage(), identityDomainException);
        ApiResponse<Object> response = ApiResponse.builder()
                .code(identityDomainException.getCode())
                .message(identityDomainException.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.valueOf(identityDomainException.getStatusCode())).body(response);
    }
}
