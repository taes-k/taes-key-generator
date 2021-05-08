package com.taes.key.generator.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private final ApiErrorCode errorCode;

    public ApiException(ApiErrorCode errorCode) {
        super(errorCode.name());
        this.errorCode = errorCode;
    }
}
