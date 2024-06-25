package com.niksob.quoting_vk_bot.config.controller.logger;

import com.niksob.quoting_vk_bot.controller.CallbackController;
import com.niksob.quoting_vk_bot.controller.logger.BaseControllerLogger;
import com.niksob.quoting_vk_bot.controller.logger.BaseControllerLoggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CallbackControllerLoggerConfig {
    private final Logger logger = LoggerFactory.getLogger(CallbackController.class);

    @Bean("callbackControllerLogger")
    public BaseControllerLogger getCallbackControllerLogger() {
        return new BaseControllerLoggerImpl(logger);
    }
}
