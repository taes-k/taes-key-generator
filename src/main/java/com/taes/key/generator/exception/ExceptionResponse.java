package com.taes.key.generator.exception;

import lombok.Getter;

@Getter
public class ExceptionResponse
{
    private final Integer code;
    private final String message;
    private final Integer status;

    public ExceptionResponse(ApiErrorCode errorCode, Integer status)
    {
        this.code = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
        this.status = status;
    }

    public ExceptionResponse(ApiErrorCode errorCode, String message, Integer status)
    {
        this.code = errorCode.getErrorCode();
        this.message = message;
        this.status = status;
    }
}
