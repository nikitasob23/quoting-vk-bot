package com.niksob.config_service.service.git;

import com.niksob.config_service.service.git.laoder.GitConfigLoader;
import com.niksob.config_service.service.yaml.merger.YamlPropertiesMerger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitConfigServiceImpl implements GitConfigService {
    private final GitConfigLoader gitConfigLoader;
    private final YamlPropertiesMerger yamlPropertiesMerger;

    @Override
    public void formYamlMicroserviceConfigs() {
        gitConfigLoader.load();
        yamlPropertiesMerger.merge();
    }
}
