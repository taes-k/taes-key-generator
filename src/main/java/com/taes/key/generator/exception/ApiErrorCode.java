package com.taes.key.generator.exception;

import lombok.Getter;

@Getter
public enum ApiErrorCode
{
    GENERIC_ERROR(10000, "서비스 수행중 에러가 발생했습니다."),
    KEY_GENERATE_ERROR(11000, "Key 생성에 실패했습니다. 다시 시도해주세요."),
    DATABASE_GENERIC_ERROR(20000, "DB 로직 수행중 에러가 발생했습니다."),
    BAD_REQUEST_PARAMETER_ERROR(40000, "Request paramter에 이상이 있습니다.");

    private final int errorCode;
    private final String message;

    ApiErrorCode(int errorCode, String message)
    {
        this.errorCode = errorCode;
        this.message = message;
    }
}
