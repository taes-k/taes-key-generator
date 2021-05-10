package com.taes.key.generator.api.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

public class KeyDto
{
    private KeyDto()
    {
        throw new IllegalStateException("DTO Outer class");
    }

    @Getter
    @ApiModel("Key 발급 Response DTO")
    public static class KeyRes
    {
        private Object value;

        public KeyRes(Object value)
        {
            this.value = value;
        }
    }
}
