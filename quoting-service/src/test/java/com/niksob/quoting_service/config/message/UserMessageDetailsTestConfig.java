package com.niksob.quoting_service.config.message;

import com.niksob.quoting_service.model.message.UserMessageDetails;
import com.niksob.quoting_service.model.user.UserId;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class UserMessageDetailsTestConfig {
    @Bean
    public UserMessageDetails getUserMessageDetails() {
        return new UserMessageDetails(new UserId(154640822), "Test message");
    }
}
