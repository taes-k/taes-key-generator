package com.taes.key.generator.api.dto.validator;

import com.taes.key.generator.api.dto.KeySetDto;
import com.taes.key.generator.api.enums.KeyGeneratorType;
import com.taes.key.generator.api.enums.KeyType;
import org.apache.commons.lang3.EnumUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class KeyTypeConstraintValidator implements ConstraintValidator<KeyTypeConstraint, KeySetDto.CreateReq>
{
    @Override
    public boolean isValid(KeySetDto.CreateReq value, ConstraintValidatorContext context)
    {
        KeyType keyType = EnumUtils.getEnumIgnoreCase(KeyType.class, value.getType());
        KeyGeneratorType keyGeneratorType = EnumUtils.getEnumIgnoreCase(KeyGeneratorType.class, value.getGenerator());

        if(keyType == KeyType.STRING)
        {
            if(keyGeneratorType == null || keyGeneratorType == KeyGeneratorType.GENERIC)
                return true;
        }

        if(keyType == KeyType.NUMBER)
        {
            if(keyGeneratorType == KeyGeneratorType.GENERIC || keyGeneratorType == KeyGeneratorType.MYSQL)
                return true;
        }

        return false;
    }
}