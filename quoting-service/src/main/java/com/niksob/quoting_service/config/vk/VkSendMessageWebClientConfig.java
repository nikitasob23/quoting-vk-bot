package com.niksob.quoting_service.config.vk;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import static com.niksob.quoting_service.uri.vk.send.VkUri.SEND_METHOD;

@Profile("!test")
@Configuration
public class VkSendMessageWebClientConfig {
    @Bean("vkSendMessageWebClient")
    public WebClient getVkSendMessageWebClient() {
        return WebClient.builder()
                .baseUrl(SEND_METHOD)
                .defaultHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }
}
