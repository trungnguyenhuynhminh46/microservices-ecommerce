package com.tuber.application.handler;

import lombok.extern.slf4j.Slf4j;
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
    public ResponseBase<Object> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseBase.builder()
                .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                .message("Unexpected error!")
                .build();
    }
}
