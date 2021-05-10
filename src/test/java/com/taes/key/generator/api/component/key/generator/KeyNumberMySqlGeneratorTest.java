package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.UnitTest;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyGeneratorType;
import com.taes.key.generator.api.enums.KeyType;
import com.taes.key.generator.api.repository.KeyNumberMySqlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

@DisplayName("KeyNumber mysql Generator 테스트")
class KeyNumberMySqlGeneratorTest
{

    @Mock
    private final KeyNumberMySqlRepository keyNumberMySqlRepository = Mockito.mock(KeyNumberMySqlRepository.class);

    @Nested
    @DisplayName("Key 발급 테스트")
    class KeyGeneratorTest extends UnitTest
    {
        @Test
        @DisplayName("발급 (Number mysql type) -> 성공")
        public void generateKey_numberMySqlType_success()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeySetSeq(1);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.NUMBER);
            keySet.setKeyGenerator(KeyGeneratorType.MYSQL);
            keySet.setMinLength(3);

            KeyGenerator keyGenerator = new KeyNumberMySqlGenerator(keyNumberMySqlRepository);
            Mockito.doReturn(null).when(keyNumberMySqlRepository).save(Mockito.any());
            Mockito.doReturn(1L).when(keyNumberMySqlRepository).findLastInsertId();

            // when
            Long result = (Long) keyGenerator.generateNewKey(keySet);

            // then
            Assertions.assertEquals(101L, result);
        }
    }
}