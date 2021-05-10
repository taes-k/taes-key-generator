package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.UnitTest;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyType;
import com.taes.key.generator.api.repository.KeyStringRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

@DisplayName("KeyString Generator 테스트")
class KeyStringGeneratorTest extends UnitTest
{

    @Mock
    private final KeyStringRepository keyStringRepository = Mockito.mock(KeyStringRepository.class);

    @Nested
    @DisplayName("Key 발급 테스트")
    class KeyGeneratorTest extends UnitTest
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

            KeyGenerator keyGenerator = new KeyStringGenerator(keyStringRepository);

            // when
            String result = (String) keyGenerator.generateNewKey(keySet);

            // then
            Assertions.assertEquals(19, StringUtils.length(result));
        }

        @Test
        @DisplayName("발급 (String type) -> 실패 (중복키)")
        public void generateKey_stringType_fail_duplicateKey()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeySetSeq(1);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.STRING);

            KeyGenerator keyGenerator = new KeyStringGenerator(keyStringRepository);
            Mockito.doThrow(new DataIntegrityViolationException("중복키 존재"))
                .when(keyStringRepository).save(Mockito.any());

            // when
            Executable exc = () -> keyGenerator.generateNewKey(keySet);

            // then
            Assertions.assertThrows(DataIntegrityViolationException.class, exc);
        }
    }

}