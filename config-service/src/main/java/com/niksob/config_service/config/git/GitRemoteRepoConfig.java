package com.niksob.config_service.config.git;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@Accessors(chain = true)
@ConfigurationProperties(prefix = "repo.remote.git")
public class GitRemoteRepoConfig {
    private String uri;
    private String username;
    private String password;
    private String propertiesFile;
}
