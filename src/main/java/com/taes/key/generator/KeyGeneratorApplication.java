package com.taes.key.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class KeyGeneratorApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(KeyGeneratorApplication.class, args);
    }

}
