package com.niksob.quoting_vk_bot.config.vk.group.confirmation;

import com.niksob.quoting_vk_bot.model.vk.group.confirmation.VkGroupConfirmation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VkGroupConfirmationConfig {
    private static final String CONFORMATION_TYPE = "confirmation";

    @Value("${vk.group.id}")
    private Long groupId;

    @Bean
    public VkGroupConfirmation getVkGroupConfirmation() {
        return new VkGroupConfirmation(CONFORMATION_TYPE, groupId);
    }
}
