package com.taes.key.generator.integration;

import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.HttpWaitStrategy;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

@Testcontainers
@Tag("IntegrationTest")
@ActiveProfiles("junit-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class TCIntegrationTest
{
    @Autowired
    protected MockMvc mockMvc;

    static final DockerComposeContainer composeContainer;

    static
    {
        composeContainer = new DockerComposeContainer(new File("src/test/resources/docker-compose.yml"));

        composeContainer.start();

        try
        {
            // Docker init wait ...
            Thread.sleep(5_000);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
