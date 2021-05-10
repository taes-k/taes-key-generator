package com.taes.key.generator.api.service;

import com.taes.key.generator.api.component.key.generator.KeyGeneratorFactory;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.repository.KeySetRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class KeyService
{
    private final KeySetRepository keySetRepository;
    private final KeyGeneratorFactory keyGeneratorServiceFactory;

    public KeyService(
        KeySetRepository keySetRepository
        , KeyGeneratorFactory keyGeneratorServiceFactory)
    {
        this.keySetRepository = keySetRepository;
        this.keyGeneratorServiceFactory = keyGeneratorServiceFactory;
    }

    @Transactional
    public void registKeySet(KeySet keySet)
    {
        if (keySetRepository.existsByKeyId(keySet.getKeyId()))
            throw new IllegalArgumentException("이미 등록된 키가 존재합니다.");

        try
        {
            keySetRepository.save(keySet);
        }
        catch (DataIntegrityViolationException e)
        {
            // race-condition 상황에서 unique key 경합 발생 가능성 존재
            throw new IllegalArgumentException("이미 등록된 키가 존재합니다.", e);
        }
    }

    public Optional<KeySet> getKeySet(String keyId)
    {
        return keySetRepository.findByKeyId(keyId);
    }

    public Object generateNewKey(KeySet keyset)
    {
        return keyGeneratorServiceFactory.generateNewKey(keyset);
    }
}
