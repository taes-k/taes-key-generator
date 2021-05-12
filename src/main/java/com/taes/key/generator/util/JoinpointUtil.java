package com.taes.key.generator.util;

import com.taes.key.generator.exception.ApiErrorCode;
import com.taes.key.generator.exception.ApiException;
import org.aspectj.lang.JoinPoint;

import java.util.Arrays;

public class JoinpointUtil
{
    private JoinpointUtil()
    {
        throw new IllegalStateException("Utility class");
    }

    public static <T> T getParamByClass(JoinPoint jp, Class<T> clazz)
    {
        return Arrays.stream(jp.getArgs())
            .filter(clazz::isInstance)
            .map(clazz::cast)
            .findFirst()
            .orElseThrow(() -> new ApiException(ApiErrorCode.ARGUMENT_ERROR));
    }

}
