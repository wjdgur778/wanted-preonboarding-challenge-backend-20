package com.example.demo.common.exception;

import ch.qos.logback.core.spi.ErrorCodes;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestApiException extends RuntimeException {
    private final ErrorCode errorCode;
}
