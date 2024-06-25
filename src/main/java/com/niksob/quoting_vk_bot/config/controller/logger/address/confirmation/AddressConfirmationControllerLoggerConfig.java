package com.niksob.quoting_vk_bot.config.controller.logger.address.confirmation;

import com.niksob.quoting_vk_bot.controller.address.confirmation.AddressConfirmationController;
import com.niksob.quoting_vk_bot.logger.controller.BaseControllerLogger;
import com.niksob.quoting_vk_bot.logger.controller.BaseControllerLoggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressConfirmationControllerLoggerConfig {
    private final Logger logger = LoggerFactory.getLogger(AddressConfirmationController.class);

    @Bean("addressConfirmationControllerLogger")
    public BaseControllerLogger getAddressConfirmationControllerLogger() {
        return new BaseControllerLoggerImpl(logger);
    }
}
