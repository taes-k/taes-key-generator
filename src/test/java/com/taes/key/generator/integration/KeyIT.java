package com.taes.key.generator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taes.key.generator.api.dto.KeyDto;
import com.taes.key.generator.util.ObjectUtil;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Key 통합 테스트")
public class KeyIT extends TCIntegrationTest
{
    @Nested
    @Sql("classpath:sql/regist-keyset.sql")
    @DisplayName("Keyset 등록 테스트")
    class KeysetTest extends TCIntegrationTest
    {
        @Test
        @DisplayName("Key 등록 -> 성공")
        void keyRegist_success() throws Exception
        {
            // given
            String url = "/api/key/register";
            String requestPath = "classpath:json/key-regist-normal.json";
            String content = FileUtils.readFileToString(ResourceUtils.getFile(requestPath), StandardCharsets.UTF_8);

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(rq)
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Key 등록 -> 실패 (중복키)")
        void keyRegist_fail_duplicate() throws Exception
        {
            // given
            String url = "/api/key/register";
            String requestPath = "classpath:json/key-regist-duplicate.json";
            String content = FileUtils.readFileToString(ResourceUtils.getFile(requestPath), StandardCharsets.UTF_8);

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(rq)
                .andExpect(status().is5xxServerError());
        }

    }

    @Nested
    @Sql("classpath:sql/generate-key-string.sql")
    @Sql("classpath:sql/generate-key-number-mysql.sql")
    @Sql("classpath:sql/generate-key-number-generic.sql")
    @DisplayName("Key 발급 테스트")
    class KeyTest extends TCIntegrationTest
    {
        private ObjectMapper mapper = new ObjectMapper();

        @Test
        @DisplayName("Key 발급 -> 실패 (미등록키)")
        void generateKey_fail_notFound() throws Exception
        {
            // given
            String url = "/api/key/not-registered-key";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .post(url);

            mockMvc.perform(rq)
                .andExpect(status().is5xxServerError());
        }

        @Test
        @DisplayName("Key 발급 (String type) -> 성공")
        void generateKey_stringType_success() throws Exception
        {
            // given
            String url = "/api/key/string-key";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            mockMvc.perform(rq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").isNotEmpty());
        }

        @Test
        @DisplayName("Key 발급 (Number mysql type) -> 성공")
        void generateKey_numberMySqlType_success() throws Exception
        {
            // given
            String url = "/api/key/number-mysql-key";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            mockMvc.perform(rq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", greaterThan(10)));
        }

        @Test
        @DisplayName("Key 발급 (Number generic type) -> 성공")
        void generateKey_numberGenericType_success() throws Exception
        {
            // given
            String url = "/api/key/number-generic-key";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            mockMvc.perform(rq)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value", greaterThan(10)));
        }


        @Test
        @DisplayName("Key 발급 (Number mysql type) -> 성공 (min-length overflow)")
        void generateKey_numberMySqlType_success_minLengthOverflow() throws Exception
        {
            // given
            String url = "/api/key/number-mysql-min-len-1-key";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            List<Long> values = new ArrayList<>();

            for (int i = 0; i < 10; i++)
            {
                values.add(ObjectUtil.toLong(getKey(rq).getValue()));
            }

            Assertions.assertTrue(values.contains(10L));
        }

        @Test
        @DisplayName("Key 발급 (Number mysql type) -> 성공 (min-length overflow)")
        void generateKey_numberGenericType_success_minLengthOverflow() throws Exception
        {
            // given
            String url = "/api/key/number-generic-min-len-1-key";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            List<Long> values = new ArrayList<>();

            for (int i = 0; i < 10; i++)
            {
                values.add(ObjectUtil.toLong(getKey(rq).getValue()));
            }

            Assertions.assertTrue(values.contains(10L));
        }

        private KeyDto.KeyRes getKey(MockHttpServletRequestBuilder rq)
        {
            try
            {
                MvcResult result = mockMvc.perform(rq)
                    .andExpect(status().isOk())
                    .andReturn();

                String content = result.getResponse().getContentAsString();
                KeyDto.KeyRes res = mapper.readValue(content, KeyDto.KeyRes.class);

                return res;
            }
            catch (Exception e)
            {
                throw new RuntimeException("Test error", e);
            }
        }
    }


    @Nested
    @Sql("classpath:sql/generate-key-string.sql")
    @Sql("classpath:sql/generate-key-number-mysql.sql")
    @Sql("classpath:sql/generate-key-number-generic.sql")
    @DisplayName("Key 발급 테스트 (multi-thread)")
    class KeyMultiThreadTest extends TCIntegrationTest
    {
        private ObjectMapper mapper = new ObjectMapper();

        @Test
        @DisplayName("Key 발급 (String type) -> 성공 (multi-thread)")
        void generateKey_stringType_success_multiThread()
        {
            // given
            String url = "/api/key/string-key-multi";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            List<CompletableFuture<KeyDto.KeyRes>> futures = new ArrayList<>();

            for (int i = 0; i < 10; i++)
            {
                futures.add(CompletableFuture.supplyAsync(() -> getKey(rq)));
            }

            Set<String> results = futures.stream()
                .map(CompletableFuture::join)
                .map(a -> a.getValue().toString())
                .collect(Collectors.toSet());

            Assertions.assertEquals(10, results.size());
        }


        @Test
        @DisplayName("Key 발급 (Number mysql type) -> 성공 (multi-thread)")
        void generateKey_numberMySqlType_success_multiThread()
        {
            // given
            String url = "/api/key/number-mysql-key-multi";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            List<CompletableFuture<KeyDto.KeyRes>> futures = new ArrayList<>();

            for (int i = 0; i < 10; i++)
            {
                futures.add(CompletableFuture.supplyAsync(() -> getKey(rq)));
            }

            Set<Long> results = futures.stream()
                .map(CompletableFuture::join)
                .map(a -> ObjectUtil.toLong(a.getValue()))
                .collect(Collectors.toSet());

            Assertions.assertEquals(10, results.size());
        }

        @Test
        @DisplayName("Key 발급 (Number generic type) -> 성공 (multi-thread)")
        void generateKey_numberGenericType_success_multiThread()
        {
            // given
            String url = "/api/key/number-generic-key-multi";

            // when-then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .get(url);

            List<CompletableFuture<KeyDto.KeyRes>> futures = new ArrayList<>();

            for (int i = 0; i < 10; i++)
            {
                futures.add(CompletableFuture.supplyAsync(() -> getKey(rq)));
            }

            Set<Long> results = futures.stream()
                .map(CompletableFuture::join)
                .map(a -> ObjectUtil.toLong(a.getValue()))
                .collect(Collectors.toSet());

            Assertions.assertEquals(10, results.size());
        }

        private KeyDto.KeyRes getKey(MockHttpServletRequestBuilder rq)
        {
            try
            {
                MvcResult result = mockMvc.perform(rq)
                    .andExpect(status().isOk())
                    .andReturn();

                String content = result.getResponse().getContentAsString();
                KeyDto.KeyRes res = mapper.readValue(content, KeyDto.KeyRes.class);

                return res;
            }
            catch (Exception e)
            {
                throw new RuntimeException("Test error", e);
            }
        }
    }
}
