package com.taes.key.generator.api.component.key.generator;

import com.taes.key.generator.UnitTest;
import com.taes.key.generator.api.entity.KeyNumberGeneric;
import com.taes.key.generator.api.entity.KeyNumberGenericSequence;
import com.taes.key.generator.api.entity.KeySet;
import com.taes.key.generator.api.enums.KeyGeneratorType;
import com.taes.key.generator.api.enums.KeyType;
import com.taes.key.generator.api.repository.KeyNumberGenericRepository;
import com.taes.key.generator.api.repository.KeyNumberGenericSequenceRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("KeyNumber generic Generator 테스트")
class KeyNumberGenericGeneratorTest
{

    @Mock
    private final KeyNumberGenericSequenceRepository keyNumberGenericSequenceRepository = Mockito.mock(KeyNumberGenericSequenceRepository.class);

    @Mock
    private final KeyNumberGenericRepository keyNumberGenericRepository = Mockito.mock(KeyNumberGenericRepository.class);


    @Nested
    @DisplayName("Key 발급 테스트")
    class KeyGeneratorTest extends UnitTest
    {
        @Test
        @DisplayName("발급 (Number generic type) -> 성공")
        public void generateKey_numberMySqlType_success()
        {
            // given
            KeySet keySet = new KeySet();
            String keyId = RandomStringUtils.random(10, true, true);
            keySet.setKeySetSeq(1);
            keySet.setKeyId(keyId);
            keySet.setDescription("test 용도 Key");
            keySet.setKeyType(KeyType.NUMBER);
            keySet.setKeyGenerator(KeyGeneratorType.GENERIC);
            keySet.setMinLength(3);

            KeyNumberGenericSequence seq = new KeyNumberGenericSequence();
            seq.setKeySetSeq(1);
            seq.setNextVal(1L);

            KeyNumberGeneric keyNumberGeneric = new KeyNumberGeneric(keySet, seq.getNextVal());


            KeyGenerator keyGenerator = new KeyNumberGenericGenerator(keyNumberGenericRepository, keyNumberGenericSequenceRepository);
            Mockito.doReturn(Optional.of(seq)).when(keyNumberGenericSequenceRepository).findById(keySet.getKeySetSeq());
            Mockito.doReturn(seq).when(keyNumberGenericSequenceRepository).save(Mockito.any());
            Mockito.doReturn(keyNumberGeneric).when(keyNumberGenericRepository).save(Mockito.any());

            // when
            Long result = (Long) keyGenerator.generateNewKey(keySet);

            // then
            Assertions.assertEquals(101L, result);
        }
    }
}