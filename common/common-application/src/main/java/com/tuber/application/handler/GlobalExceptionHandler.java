package com.tuber.application.handler;

import com.tuber.domain.exception.DomainException;
import lombok.extern.slf4j.Slf4j;
import com.tuber.domain.constant.ResponseCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Object> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ApiResponse.builder()
                .code(ResponseCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message("Unexpected error!")
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Object> handleException(DomainException domainException) {
        log.error(domainException.getMessage(), domainException);
        return ApiResponse.builder()
                .code(domainException.getResponseCode().getCode())
                .message(domainException.getMessage())
                .build();
    }
}
