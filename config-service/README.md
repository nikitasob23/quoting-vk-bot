# Config-service Guide
Open API v1.0.0

## Overview
This microservice retrieves application settings from a GitHub repository, forms them into a single configuration file for each microservice according to specified rules, and delivers them upon request in JSON format.

## Dependencies
The config-service project utilizes the following Maven dependencies to ensure functionality, configuration management, and other key aspects of the service:

### Spring Boot
1. **Spring Boot Starter** - dependency for enabling Spring functionalities
2. **Spring Boot Starter Test** - library for testing support

### Configuration Retrieval, Formation, and Delivery
1. **Config Server** - serves as a centralized repository for configuration files and provides an API for fetching them. In this project, the Config Server plays a crucial role in managing and distributing configurations for all microservices, enabling centralized configuration management and simplifying maintenance when configuration changes are needed.
2. **Git Connector** - library providing an API for interacting with the GitHub repository where configuration files are stored
3. **Yaml** - library that allows combining multiple settings from downloaded configurations into the few files needed for microservice operations

### Logging
**Logback** - the standard logging system used in the microservice

### Miscellaneous
**Lombok** - used to reduce boilerplate code

## Configuration
### 1. Loading Configurations from the GitHub Repository
The microservice initially loads configuration files from a private GitHub repository.
```yaml
repo:
  remote:
    git:
      uri: https://github.com/nikitasob23/my-mood-tracker-config.git
      username: nikitasob23
      password: [PASSWORD]
      properties-file: merge_properties
  local:
    git:
      path: app/git_configs
    config:
      path: app/service_configs
```
1. **repo.remote.git.uri** - GitHub repository URL
2. **repo.remote.git.username** and **repo.remote.git.password** - GitHub login and password for access
3. **repo.remote.git.properties-file** - the name of the file containing rules for creating configuration files for each microservice
4. **repo.local.git.path** - local directory for storing the Git repository
5. **repo.local.config.path** - local directory where the configuration files for microservices will be stored

Example `properties-file.yml`:
```yaml
database_service:
  - logger
  - database_connection
  - database_properties
authorization_service:
  - logger
  - database_connection
  - authorization_connection
  - authorization_properties
  - mail_sender_connection
mail_sender:
  - logger
  - mail_sender_connection
  - mail_sender_properties
  - gateway_connection
gateway_service:
  - logger
  - gateway_connection
  - authorization_connection
  - database_connection
  - mail_sender_connection
  - gateway_properties
```
The file names listed in the arrays are actual files in the repository. Settings from these files will be compiled and added to `database_service.yml`, `authorization_service.yml`, `mail_sender.yml`, `gateway_service.yml`, and then sent to the microservices upon request.

### 2. Spring Cloud
config-service downloads configurations from the remote git repository, then compiles the settings for the microservices. Spring Cloud then retrieves these ready-made configurations and distributes them to the microservices. To enable this, specify the **_native_** profile when starting the application and the directory where the configuration files are stored:
```yaml
spring:
  cloud:
    config:
      server:
        native:
          search-locations: file:///${repo.local.config.path}
```

With these minimal settings in place, the service is ready to operate.

### Additional Documentation Links
For more information, please refer to the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)
