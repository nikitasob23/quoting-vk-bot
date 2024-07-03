package com.niksob.config_service;

import com.ulisesbocchio.jasyptspringboot.environment.StandardEncryptableEnvironment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConfigServiceApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ConfigServiceApplication.class);
        application.setEnvironment(new StandardEncryptableEnvironment());
        application.run(args);
    }
}
