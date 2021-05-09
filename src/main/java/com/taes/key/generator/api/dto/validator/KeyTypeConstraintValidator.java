package com.taes.key.generator.api.dto.validator;

import com.taes.key.generator.api.dto.KeySetDto;
import com.taes.key.generator.api.enums.KeyGenerator;
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
        KeyGenerator keyGenerator = EnumUtils.getEnumIgnoreCase(KeyGenerator.class, value.getGenerator());

        if(keyType == KeyType.STRING)
        {
            if(keyGenerator == null || keyGenerator == KeyGenerator.GENERIC)
                return true;
        }

        if(keyType == KeyType.NUMBER)
        {
            if(keyGenerator == KeyGenerator.GENERIC || keyGenerator == KeyGenerator.MYSQL)
                return true;
        }

        return false;
    }
}