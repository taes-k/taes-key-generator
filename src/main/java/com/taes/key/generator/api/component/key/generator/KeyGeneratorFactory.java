package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyType;
import org.springframework.stereotype.Component;

@Component
public class KeyGeneratorFactory
{
    private final KeyGenerator KeyStringGenerator;

    public KeyGeneratorFactory(KeyStringGenerator KeyStringGenerator)
    {
        this.KeyStringGenerator = KeyStringGenerator;
    }

    public Object generateNewKey(KeySet keySet)
    {
        if (keySet.getKeyType() == KeyType.STRING)
            return KeyStringGenerator.generateNewKey(keySet);

        throw new IllegalArgumentException(
            String.format(
                "비정상 Key type 입니다. KeyType : %s / Generator : %s"
                , keySet.getKeyType()
                , keySet.getGenerator()));
    }
}
