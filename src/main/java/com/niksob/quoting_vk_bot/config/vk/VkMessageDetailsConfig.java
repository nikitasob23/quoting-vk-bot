package com.niksob.quoting_vk_bot.config.vk;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@Getter
@Setter
@ConfigurationProperties(prefix = "vk")
public class VkMessageDetailsConfig {
    private String token;
    private String apiVersion;
    private String messageTemplate;
}
