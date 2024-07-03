package com.niksob.config_service.config.app;

import com.niksob.config_service.service.git.GitConfigService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class GitConfigServiceInitializer {
    private final GitConfigService gitConfigService;

    @PostConstruct
    public void init() {
        gitConfigService.formYamlMicroserviceConfigs();
    }
}
