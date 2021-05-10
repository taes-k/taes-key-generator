package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.api.entity.KeyNumberMySql;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.repository.KeyNumberMySqlRepository;
import com.taes.key.generator.exception.ApiErrorCode;
import com.taes.key.generator.exception.ApiException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KeyNumberMySqlGenerator implements KeyGenerator<Long>
{
    private final KeyNumberMySqlRepository keyNumberMySqlRepository;

    public KeyNumberMySqlGenerator(KeyNumberMySqlRepository keyNumberMySqlRepository)
    {
        this.keyNumberMySqlRepository = keyNumberMySqlRepository;
    }

    @Retryable(value = DataIntegrityViolationException.class, maxAttempts = 10)
    @Override
    public Long generateNewKey(KeySet keySet)
    {
        KeyNumberMySql keyNumber = new KeyNumberMySql(keySet);
        keyNumberMySqlRepository.save(keyNumber);
        long keyValue = keyNumberMySqlRepository.findLastInsertId();

        return getCorrectionValue(keySet, keyValue);
    }

    /**
     * minLength에 따른 보정값을 반환하는 메서드
     */
    private long getCorrectionValue(KeySet keySet, long value)
    {
        Integer minLength = keySet.getMinLength();

        // minLength < 2 일때 보정값은 0
        int exponent = ObjectUtils.isEmpty(minLength) || minLength < 2
            ? -1
            : minLength - 1;

        int correctValue = (int) Math.pow(10, exponent);

        return correctValue + value;
    }

    @Recover
    @SuppressWarnings("unused")
    private void recover(DataIntegrityViolationException e)
    {
        throw new ApiException(ApiErrorCode.KEY_GENERATE_ERROR);
    }
}
