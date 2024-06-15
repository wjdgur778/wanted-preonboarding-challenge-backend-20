package com.example.demo.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    private T Data;
    private String message;
}
