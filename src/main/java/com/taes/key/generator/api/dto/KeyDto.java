package com.taes.key.generator.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class KeyDto
{
    private KeyDto()
    {
        throw new IllegalStateException("DTO Outer class");
    }

    @NoArgsConstructor
    @Setter
    @Getter
    @ApiModel("Key 발급 Response DTO")
    public static class KeyRes
    {
        @JsonProperty("value")
        private Object value;

        public KeyRes(Object value)
        {
            this.value = value;
        }

        @Override
        public String toString()
        {
            return "KeyRes{" +
                "value=" + value +
                '}';
        }
    }
}
