package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyGeneratorType;
import com.taes.key.generator.api.enums.KeyType;
import org.springframework.stereotype.Component;

@Component
public class KeyGeneratorFactory
{
    private final KeyGenerator keyStringGenerator;
    private final KeyGenerator keyNumberMySqlGenerator;
    private final KeyGenerator keyNumberGenericGenerator;

    public KeyGeneratorFactory(
        KeyStringGenerator keyStringGenerator
        , KeyNumberMySqlGenerator keyNumberMySqlGenerator
        , KeyNumberGenericGenerator keyNumberGenericGenerator)
    {
        this.keyStringGenerator = keyStringGenerator;
        this.keyNumberMySqlGenerator = keyNumberMySqlGenerator;
        this.keyNumberGenericGenerator = keyNumberGenericGenerator;
    }

    public Object generateNewKey(KeySet keySet)
    {
        if (keySet.getKeyType() == KeyType.STRING)
            return keyStringGenerator.generateNewKey(keySet);

        if (keySet.getKeyType() == KeyType.NUMBER && keySet.getKeyGenerator() == KeyGeneratorType.MYSQL)
            return keyNumberMySqlGenerator.generateNewKey(keySet);

        if (keySet.getKeyType() == KeyType.NUMBER && keySet.getKeyGenerator() == KeyGeneratorType.GENERIC)
            return keyNumberGenericGenerator.generateNewKey(keySet);


        throw new IllegalArgumentException(
            String.format(
                "비정상 Key type 입니다. KeyType : %s / Generator : %s"
                , keySet.getKeyType()
                , keySet.getKeyGenerator()));
    }
}
