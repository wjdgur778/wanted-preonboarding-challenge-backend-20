package com.example.demo.common.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class Result<T> {
    private T Data;
    private String message;
    private HttpStatus httpStatus;
}
