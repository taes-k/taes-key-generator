package com.taes.key.generator.api.service;

import com.taes.key.generator.UnitTest;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyType;
import com.taes.key.generator.api.repository.KeySetRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("KeyService 테스트")
class KeyServiceTest extends UnitTest
{
    @Mock
    private KeySetRepository keySetRepository = Mockito.mock(KeySetRepository.class);

    @Test
    @DisplayName("Keyset 등록 테스트 -> 성공")
    public void keySet_save_success()
    {
        // given
        KeySet keySet = new KeySet();
        String keyId = RandomStringUtils.random(10, true, true);
        keySet.setKeyId(keyId);
        keySet.setDescription("test 용도 Key");
        keySet.setKeyType(KeyType.STRING);

        KeyService keyService = new KeyService(keySetRepository);
        Mockito.doReturn(Optional.empty()).when(keySetRepository).findById(keyId);
        Mockito.doReturn(keySet).when(keySetRepository).save(keySet);

        // when
        keyService.registKeySet(keySet);

        // then
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("Keyset 등록 테스트 -> 실패 (Duplicate)")
    public void keySet_save_fail_duplicate()
    {
        // given
        KeySet keySet = new KeySet();
        String keyId = RandomStringUtils.random(10, true, true);
        keySet.setKeyId(keyId);
        keySet.setDescription("test 용도 Key");
        keySet.setKeyType(KeyType.STRING);

        KeyService keyService = new KeyService(keySetRepository);
        Mockito.doReturn(Optional.of(keySet)).when(keySetRepository).findById(keyId);

        // when
        Executable exc = () -> keyService.registKeySet(keySet);

        // then
        Assertions.assertThrows(IllegalArgumentException.class, exc);
    }
}