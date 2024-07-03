# Руководство сервиса config-service
Open API v1.0.0

## Обзор:
Данный микросервис получает настройки приложения из github репозитория, по инструкции формирует из них по одному общему файлу конфигурации для каждого микросервиса и отправляет их по запросу в формате JSON.

## Зависимости:
Проект config-service использует следующие Maven зависимости для обеспечения функциональности, управление конфигурациями и других ключевых аспектов сервиса:

### Spring boot:
1. **Spring Boot Starter** - зависимость, обеспечивающая работу Spring
2. **Spring boot starter test** - библиотека для работы с тестами

### Скачивание, формирование и отправка конфигураций:
1. **Config server** - является централизованным хранилищем конфигурационных файлов и предоставляет API для их извлечения. В нашем проекте Config Server играет ключевую роль в управлении и распределении конфигураций для всех микросервисов. Это позволяет централизованно управлять настройками и упрощает обслуживание при изменениях в конфигурации.
2. **Git connector** - библиотека, предоставляющая API для взаимодействия с github репозиторием, в котором находятся файлы конфигурации
3. **Yaml** - библиотека, позволяющая компоновать из множества настроек в скаченных конфигурациях лишь несколько файлов, необходимых для работы микросервисов.

### Логирование:
**Logback** - в микросервисе используется стандартная система логирования Spring

### Прочее:
**Lombok** - используется для уменьшения количества шаблонного кода

## Конфигурация:
### 1. Загрузка конфигураций из github репозитория:
Для начала микросервис загружает файлы конфигурации из приватного репозитория github 
```
repo:
  remote:
    git:
      uri: https://github.com/nikitasob23/quoting-vk-bot-config
      username: [USERNAME]
      password: [PASSWORD]
      properties-file: merge_properties
  local:
    git:
      path: app/git_configs
    config:
      path: app/service_configs
```
1. **repo.remote.git.uri** - адрес до github репозитория 
2. **repo.remote.git.username** и **repo.remote.git.password** - логин и пароль github для доступа к приватному репозиторию
3. **repo.remote.git.properties-file** - это название файла с правилами, по которым создаются файлы конфигурации для каждого микросервиса
4. **repo.local.git.path** - локальная директория для хранения git репозитория
5. **repo.local.config.path** - локальная директория, в которой будут храниться файлы конфигурации, предназначенные для микросервисов.

Пример properties-file.yml:
```
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
Названия файлов, представленные в виде списков - это фактические файлы, которые находятся в репозитории. Настройки из данных файлов будут скомпонованы и добавлены в **database_service.yml, authorization_service.yml, mail_sender.yml, gateway_service.yml** и далее оправлены по запросу в микросервисы.  

### 2. Spring cloud
config-service скачивает конфигурации из удаленного git репозитория, затем компонует настройки для микросервисов. Далее Spring cloud должен брать готовые настройки и передавать их микросервисам. Для этого нужно указать **_native_** профиль при запуске приложения и директорию, откуда нужно брать файлы конфигурации:
```
spring:
  cloud:
    config:
      server:
        native:
          search-locations: file:///${repo.local.config.path}
```

После указания данных минимальный настроек сервис готов к работе.

### Дополнительные ссылки на документацию
Для получения дополнительной информации, пожалуйста, ознакомьтесь со следующими разделами:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)
