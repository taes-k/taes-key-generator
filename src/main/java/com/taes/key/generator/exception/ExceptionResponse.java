package com.taes.key.generator.exception;

import org.springframework.dao.DataAccessException;

public class ExceptionResponse {
    private final int code;
    private final String message;

    public ExceptionResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(ApiErrorCode errorCode) {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }

    public ExceptionResponse(ApiErrorCode errorCode, String message) {
        this.code = errorCode.getErrorCode();
        this.message = message;
    }
}
