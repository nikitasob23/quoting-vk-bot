package com.niksob.config_service.config;

import com.niksob.config_service.config.git.GitRemoteRepoConfig;
import com.niksob.config_service.service.git.laoder.GitConfigLoader;
import com.niksob.config_service.service.git.laoder.GitConfigLoaderImpl;
import com.niksob.config_service.util.file.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
@AllArgsConstructor
@Profile("prod")
public class GitConfigLoaderTestConfig {
    private final String LOCAL_REPO_URI = "/Users/nickworker/Documents/Repo/my-mood-tracker-server/config-service/service-configs";

    private final FileUtil fileUtil;

    @Primary
    @Bean
    public GitConfigLoader getGitConfigLoader() {
        final GitRemoteRepoConfig gitRemoteRepoTestConfig = new GitRemoteRepoConfig()
                .setUri("TEST_URI")
                .setUsername("TEST_USERNAME")
                .setPassword("TEST_PASSWORD");
        return new GitConfigLoaderImpl(fileUtil, gitRemoteRepoTestConfig, LOCAL_REPO_URI);
    }
}
