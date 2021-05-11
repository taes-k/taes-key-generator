package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.api.entity.KeyNumberGeneric;
import com.taes.key.generator.api.entity.KeyNumberGenericSequence;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.repository.KeyNumberGenericRepository;
import com.taes.key.generator.api.repository.KeyNumberGenericSequenceRepository;
import com.taes.key.generator.exception.ApiErrorCode;
import com.taes.key.generator.exception.ApiException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Log4j2
@Component
public class KeyNumberGenericGenerator implements KeyGenerator<Long>
{
    private final KeyNumberGenericRepository keyNumberGenericRepository;
    private final KeyNumberGenericSequenceRepository keyNumberGenericSequenceRepository;

    public KeyNumberGenericGenerator(
        KeyNumberGenericRepository keyNumberGenericRepository
        , KeyNumberGenericSequenceRepository keyNumberGenericSequenceRepository)
    {
        this.keyNumberGenericRepository = keyNumberGenericRepository;
        this.keyNumberGenericSequenceRepository = keyNumberGenericSequenceRepository;
    }

    @Retryable(value = ObjectOptimisticLockingFailureException.class, maxAttempts = 10)
    @Override
    @Transactional
    public Long generateNewKey(KeySet keySet)
    {
        KeyNumberGenericSequence seq =
            keyNumberGenericSequenceRepository.findById(keySet.getKeySetSeq())
            .orElseGet(() -> {
                KeyNumberGenericSequence newSeq = new KeyNumberGenericSequence();
                newSeq.setKeySetSeq(keySet.getKeySetSeq());
                newSeq.setNextVal(0L);

                return newSeq;
            });

        seq.addNextVal();
        seq = keyNumberGenericSequenceRepository.save(seq);

        KeyNumberGeneric keyNumber = new KeyNumberGeneric(keySet, seq.getNextVal());
        keyNumber = keyNumberGenericRepository.save(keyNumber);
        long keyValue = keyNumber.getKeyValue();

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
