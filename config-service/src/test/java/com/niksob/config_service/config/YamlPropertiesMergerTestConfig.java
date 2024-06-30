package com.niksob.config_service.config;

import com.niksob.config_service.service.yaml.merger.YamlPropertiesMerger;
import com.niksob.config_service.service.yaml.merger.YamlPropertiesMergerImpl;
import com.niksob.config_service.util.file.FileUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
@AllArgsConstructor
public class YamlPropertiesMergerTestConfig {
    private final FileUtil fileUtil;

    private final String propertiesFile = "git-merge-properties";
    private final String gitRepoPath = "/Users/nickworker/Documents/Repo/my-mood-tracker-server/config-service/git-configs";
    private final String configPath = "/Users/nickworker/Documents/Repo/my-mood-tracker-server/config-service/service-configs";

    @Primary
    @Bean
    public YamlPropertiesMerger getYamlPropertiesMerger() {
        return new YamlPropertiesMergerImpl(fileUtil, propertiesFile, gitRepoPath, configPath);
    }
}
