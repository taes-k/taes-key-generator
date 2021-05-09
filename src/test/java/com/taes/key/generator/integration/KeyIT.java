package com.taes.key.generator.integration;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.ResourceUtils;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Key 통합 테스트")
public class KeyIT extends TCIntegrationTest
{
    @Nested
    @Sql("classpath:sql/regist-keyset.sql")
    @DisplayName("Keyset 테스트")
    class KeysetTest extends TCIntegrationTest
    {
        @Test
        @DisplayName("Key 등록 -> 성공")
        void keyRegist_success() throws Exception
        {
            // when
            String url = "/api/key/register";
            String requestPath = "classpath:json/key-regist-normal.json";
            String content = FileUtils.readFileToString(ResourceUtils.getFile(requestPath), StandardCharsets.UTF_8);

            // then
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
            // when
            String url = "/api/key/register";
            String requestPath = "classpath:json/key-regist-duplicate.json";
            String content = FileUtils.readFileToString(ResourceUtils.getFile(requestPath), StandardCharsets.UTF_8);

            // then
            MockHttpServletRequestBuilder rq = MockMvcRequestBuilders
                .post(url)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

            mockMvc.perform(rq)
                .andExpect(status().is5xxServerError());
        }

    }
}
