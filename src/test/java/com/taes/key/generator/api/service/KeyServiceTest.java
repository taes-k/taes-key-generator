package com.taes.key.generator.api.service;

import com.taes.key.generator.UnitTest;
import com.taes.key.generator.api.component.key.generator.KeyGeneratorFactory;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyType;
import com.taes.key.generator.api.repository.KeySetRepository;
import com.taes.key.generator.util.RandomKeyUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("KeyService 테스트")
class KeyServiceTest extends UnitTest
{
    @Mock
    private final KeySetRepository keySetRepository = Mockito.mock(KeySetRepository.class);

    @Mock
    private final KeyGeneratorFactory keyGeneratorFactory = Mockito.mock(KeyGeneratorFactory.class);

    @Nested
    @DisplayName("Keyset 테스트")
    class KeysetTest extends UnitTest
    {
        @Test
        @DisplayName("등록 -> 성공")
        public void saveKeySet_success()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.STRING);

            KeyService keyService = new KeyService(keySetRepository, keyGeneratorFactory);
            Mockito.doReturn(false).when(keySetRepository).existsByKeyId(keyId);
            Mockito.doReturn(keySet).when(keySetRepository).save(keySet);

            // when
            keyService.registKeySet(keySet);

            // then
            Assertions.assertTrue(true);
        }

        @Test
        @DisplayName("등록 -> 실패 (Duplicate)")
        public void saveKeySet_fail_duplicate()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.STRING);

            KeyService keyService = new KeyService(keySetRepository, keyGeneratorFactory);
            Mockito.doReturn(true).when(keySetRepository).existsByKeyId(keyId);

            // when
            Executable exc = () -> keyService.registKeySet(keySet);

            // then
            Assertions.assertThrows(IllegalArgumentException.class, exc);
        }

        @Test
        @DisplayName("조회 -> 성공")
        public void selectKeySet_success()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.STRING);

            KeyService keyService = new KeyService(keySetRepository, keyGeneratorFactory);
            Mockito.doReturn(Optional.of(keySet)).when(keySetRepository).findByKeyId(keyId);

            // when
            KeySet result = keyService.getKeySet(keyId).orElse(null);

            // then
            Assertions.assertEquals(keyId, result.getKeyId());
        }


        @Test
        @DisplayName("조회 -> 실패 (미등록키)")
        public void selectKeySet_fail_notFound()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.STRING);

            KeyService keyService = new KeyService(keySetRepository, keyGeneratorFactory);
            Mockito.doReturn(Optional.empty()).when(keySetRepository).findByKeyId(keyId);

            // when
            KeySet result = keyService.getKeySet(keyId).orElse(null);

            // then
            Assertions.assertNull(result);
        }
    }


    @Nested
    @DisplayName("Key 테스트")
    class KeyTest extends UnitTest
    {
        @Test
        @DisplayName("발급 (String type) -> 성공")
        public void generateKey_stringType_success()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeySetSeq(1);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.STRING);

            KeyService keyService = new KeyService(keySetRepository, keyGeneratorFactory);
            Mockito.doReturn(RandomKeyUtil.generateRandomStringKey()).when(keyGeneratorFactory).generateNewKey(keySet);

            // when
            String result = (String) keyService.generateNewKey(keySet);

            // then
            Assertions.assertEquals(19, StringUtils.length(result));
        }
    }
}