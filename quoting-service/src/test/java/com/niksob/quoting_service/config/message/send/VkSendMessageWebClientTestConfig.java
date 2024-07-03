package com.niksob.quoting_service.config.message.send;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Profile("test")
@TestConfiguration
public class VkSendMessageWebClientTestConfig {
    @Primary
    @Bean("vkSendMessageWebClient")
    public WebClient getTestVkSendMessageWebClient() {
        return WebClient.builder()
                .baseUrl("TEST_SEND_METHOD")
                .defaultHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }
}