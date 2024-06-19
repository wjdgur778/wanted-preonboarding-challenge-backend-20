package com.example.demo.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class Result<T> {
    private T Data;
    private String message;
    private HttpStatus httpStatus;
}
