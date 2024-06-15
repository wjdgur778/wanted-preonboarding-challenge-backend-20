package com.example.demo.common.exception;

import com.example.demo.common.model.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<Result> handleRestApiException(RestApiException re) {
        return ResponseEntity.status(re.getErrorCode().getStatus()).body(Result.builder()
                .message(re.getErrorCode().getMessage())
                .httpStatus(re.getErrorCode().getStatus())
                .build()
        );
    }

}