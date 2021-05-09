package com.taes.key.generator.api.service;

import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.repository.KeySetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class KeyService
{
    private final KeySetRepository keySetRepository;

    public KeyService(KeySetRepository keySetRepository)
    {
        this.keySetRepository = keySetRepository;
    }

    @Transactional
    public void registKeySet(KeySet keySet)
    {
        keySetRepository.findById(keySet.getKeyId())
            .ifPresent(k ->
            {
                throw new IllegalArgumentException("이미 등록된 키가 존재합니다.");
            });

        keySetRepository.save(keySet);
    }
}
