package com.niksob.config_service.service;

import com.niksob.config_service.MainContextTest;
import com.niksob.config_service.config.YamlPropertiesMergerTestConfig;
import com.niksob.config_service.service.yaml.merger.YamlPropertiesMerger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = YamlPropertiesMergerTestConfig.class)
@ActiveProfiles(profiles = {"prod", "native"})
public class YamlPropertiesMergerTest extends MainContextTest {
    @Autowired
    private YamlPropertiesMerger yamlPropertiesMerger;

    @Test
    public void testMerging() {
        try {
        yamlPropertiesMerger.merge();
        } catch (Exception e) {
            Assertions.fail("Method throws exception" + e.getMessage());
        }
    }
}
