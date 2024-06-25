package com.niksob.quoting_vk_bot.config.vk.group.confirmation.code;

import com.niksob.quoting_vk_bot.model.vk.group.confirmation.code.ConfirmationCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmationCodeConfig {
    @Value("${vk.confirmation.address.code}")
    private String code;

    @Bean
    public ConfirmationCode getConfirmationCode() {
        return new ConfirmationCode(code);
    }
}
