package com.niksob.config_service.service.yaml.merger;

import com.niksob.config_service.util.file.FileUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class YamlPropertiesMergerImpl implements YamlPropertiesMerger {
    private static final String YAML_FORMAT = ".yml";

    private final FileUtil fileUtil;

    @Value("${repo.remote.git.properties-file}")
    private String mergePropertiesFile;
    @Value("${repo.local.git.path}")
    private String gitRepoPath;
    @Value("${repo.local.config.path}")
    private String configPath;

    @Autowired
    public YamlPropertiesMergerImpl(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    @Override
    public void merge() {
        log.info("Start merging config files in directory: {}", gitRepoPath);
        fileUtil.dropDir(configPath);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);
        final Yaml yaml = new Yaml(new Constructor(Map.class, loaderOptions));
        final String mergePropertiesPath = gitRepoPath + "/" + mergePropertiesFile + YAML_FORMAT;

        try {
            Map<String, List<String>> mergeProperties = yaml.load(new FileInputStream(mergePropertiesPath));

            for (String service : mergeProperties.keySet()) {
                Map<String, Object> combinedProperties = new LinkedHashMap<>();
                for (String include : mergeProperties.get(service)) {
                    final String path = gitRepoPath + "/" + include + YAML_FORMAT;
                    Map<String, Object> properties = yaml.load(new FileInputStream(path));
                    deepMerge(combinedProperties, properties);
                }
                final String serviceConfigPath = configPath + "/" + service + YAML_FORMAT;
                new File(serviceConfigPath).getParentFile().mkdirs();
                saveYaml(serviceConfigPath, combinedProperties);
                log.info("Yaml property file was saved: {}", serviceConfigPath);
            }
        } catch (IOException e) {
            log.error("Error during read and merge properties", e);
        }
    }

    private void deepMerge(Map<String, Object> original, Map<String, Object> newMap) {
        for (String key : newMap.keySet()) {
            if (newMap.get(key) instanceof Map && original.get(key) instanceof Map) {
                Map<String, Object> originalSubMap = (Map<String, Object>) original.get(key);
                Map<String, Object> newSubMap = (Map<String, Object>) newMap.get(key);
                deepMerge(originalSubMap, newSubMap);
            } else {
                original.put(key, newMap.get(key));
            }
        }
    }

    private void saveYaml(String filename, Map<String, Object> data) throws IOException {
        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(filename);
        yaml.dump(data, writer);
    }
}
