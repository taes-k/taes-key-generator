package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.api.entity.KeySet;

public interface KeyGenerator<T>
{
    T generateNewKey(KeySet keySet);
}
