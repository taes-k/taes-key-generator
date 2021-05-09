package com.taes.key.generator.api.dto;

import com.taes.key.generator.api.dto.validator.KeyTypeConstraint;
import com.taes.key.generator.util.RegexUtil;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class KeySetDto
{
    private KeySetDto()
    {
        throw new IllegalStateException("DTO Outer class");
    }

    @KeyTypeConstraint(message = "지원하지 않는 Key type 입니다. 'type'과 'generator'를 확인해 주세요.")
    @ApiModel("KeySet 생성 Request DTO")
    @Getter
    @Setter
    public static class CreateReq
    {
        @NotBlank
        @Pattern(regexp = RegexUtil.REGEX_ALPHABET_NUMBER_SIGN)
        private String key;
        private String description;
        @NotBlank
        private String type;
        private String generator;
        private Integer minLength;
    }
}
