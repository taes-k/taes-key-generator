package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.entity.KeyString;
import com.taes.key.generator.api.repository.KeyStringRepository;
import com.taes.key.generator.exception.ApiErrorCode;
import com.taes.key.generator.exception.ApiException;
import com.taes.key.generator.util.RandomKeyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KeyStringGenerator implements KeyGenerator<String>
{
    private final KeyStringRepository keyStringRepository;

    public KeyStringGenerator(KeyStringRepository keyStringRepository)
    {
        this.keyStringRepository = keyStringRepository;
    }

    @Retryable(value = DataIntegrityViolationException.class, maxAttempts = 10)
    @Override
    public String generateNewKey(KeySet keySet)
    {
        String newKey = RandomKeyUtil.generateRandomStringKey();
        KeyString keyString = new KeyString(keySet, newKey);
        keyStringRepository.save(keyString);

        return newKey;
    }

    @Recover
    @SuppressWarnings("unused")
    private void recover(DataIntegrityViolationException e)
    {
        throw new ApiException(ApiErrorCode.KEY_GENERATE_ERROR);
    }
}
