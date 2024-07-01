package com.niksob.quoting_service.config.vk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vk")
@Data
public class VkMessageDetailsConfig {
    private String token;
    private String apiVersion;
    private String messageTemplate;
}
