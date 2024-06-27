package com.niksob.quoting_vk_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class QuotingVkBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotingVkBotApplication.class, args);
    }

}
