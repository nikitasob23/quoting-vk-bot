# Руководство по использованию quoting-vk-bot

## Краткое описание

Данный проект представляет собой VK бота, который цитирует сообщения пользователей в переписке. Проект разработан в соответствии с [тестовым заданием](https://docs.google.com/document/d/1x_EEtb1AbU83dlJHVnI4GYkoSnGmXYmzqoM6q_BceF0/edit).
Приложение размещено на сервере [moodtracker.ru/vk_bot](moodtracker.ru/vk_bot). Чтобы воспользоваться функциями бота, необходимо написать сообщение в [сообщество](https://vk.com/mygreatquotesss). 
После этого бот автоматически отправит вам цитату из вашего сообщения.

## Деплой
Приложение размещено на ресурсе moodtracker.ru, где находится моё основное портфолио. 
Развертывание осуществляется в контейнере с помощью [Docker Swarm](https://docs.docker.com/engine/swarm/).

docker-compose.yml
```yaml
version: '3.4'
services:
  quoting-vk-bot:
    image: niksob/quoting-vk-bot:quoting-vk-bot-1.0.0
    ports:
      - '8080:8080'
    secrets:
      - vk_jasypt_secret
    networks:
      - bot-main-network
    environment:
      SPRING_PROFILES_ACTIVE: prod

secrets:
  vk_jasypt_secret:
    external: true

networks:
  bot-main-network:
    external: true
```
В данном файле описан сервис quoting-vk-bot. 
Объявлен секрет vk_jasypt_secret, содержащий ключ для дешифровки чувствительных данных, и сеть bot-main-network для работы сервиса. 
Приложение доступно на порту 8080.

Dockerfile:
```Dockerfile
FROM alpine:3.19.1

RUN apk update
RUN apk add --no-cache openjdk17

COPY target/quoting-vk-bot-1.0.0.jar app/quoting-vk-bot-1.0.0.jar
COPY scripts/docker/start_container_app.sh app/start_app.sh

RUN chmod +x /app/start_app.sh

ENTRYPOINT ["app/start_app.sh", "app/quoting-vk-bot-1.0.0.jar"]
```
Докер-образ создается на основе легковесной версии Linux: [alpine](https://hub.docker.com/_/alpine). 
Устанавливается JDK, копируется jar-файл приложения и скрипт для запуска.

Скрипт запуска приложения:
```shell
#!/bin/sh
export VK_JASYPT_SECRET=$(cat /run/secrets/vk_jasypt_secret)

if [ -z "$1" ]; then
  echo "Error: No JAR file path provided."
  exit 1
else
  echo "Starting Java application at $1"
  exec java -jar "$1"
fi
```
Этот скрипт внутри контейнера устанавливает переменную среды VK_JASYPT_SECRET из секрета vk_jasypt_secret и запускает сервис. 
Ключ для дешифровки приложение получает через переменную среды.

## Зависимости
Проект **quoting-vk-bot** использует следующие Maven зависимости для обеспечения функциональности:
1. **Spring Boot Starter** - зависимость, обеспечивающая работу Spring
2. **spring Web Flux** - фреймворк, использующийся для асинхронной и реактивной обработки веб-запросов

### Безопасность
1. **jasypt-spring-boot-starter** - для шифрования чувствительных настроек

### Логирование
1. **Slf4j-api** - простой и унифицированный API для различных систем логирования.
2. **Logback-classic** - реализация системы логирования, предоставляющая гибкие возможности для конфигурации и управления логами.  

### Прочее
1. **Lombok** - используется для уменьшения шаблонного кода
2. **MapStruct** - фреймворк для маппинга разных моделей и сущностей, уменьшает количество кода и потенциальных ошибок при преобразовании данных


## Конфигурация
### 1. Настройки VK
```yaml
vk:
  token: ENC(AMOzCohxSnvHTvoMdgH+eJyrN98nS25bs9Qw7xlWgHb882sfXGwFSM0UXWiPlD7usIsR7MBV4xg4jpKQYkquXXc6s1Vau97LZ4We3S9Xqs3Q0U4m0JFMHb6tzo9I0DgvMplYOUtIpbWgq/qyA95JuKSG3f+nCYYPnAnSHgl4HEwAK9boTyiLur9uazXjckWef7cQggHW+GnRTtA0iDLqgV3a0n/EmmlrTsrT3i3wsThoqnXmZPIzfRw5acdd2mRzhrlJJoNjW4eKLMkSZmZIjrNJZ4of+eK9VjyvQwjODilW0vedrIl0SR46BDYHpabW)
  api_version: 5.199
  confirmation:
    group_id: 226335516
    code: ENC(E1JMv7KUdwLMzN8jwDst9iH3b78DeVJFQkLGFmIlg9I=)
  message_template: "Вы сказали: %s"
```
- **token, api_version** - необходимые параметры для отправки сообщений. Токен зашифрован с помощью _jasypt_.
- **confirmation.group_id, confirmation.code** - параметры для подтверждения адреса сервера.
- **message_template** - шаблон для отправки сообщения, в который вставляется цитата пользователя 

### 2. Настройки шифрования чувствительных данных
```yaml
jasypt:
  encryptor:
    password: ${VK_JASYPT_SECRET}
    algorithm: PBEWithMD5AndDES
```
Шифрование выполняется с помощью jasypt, ключ шифрования подгружается в переменную окружения VK_JASYPT_SECRET в контейнер приложения через систему управления секретами в Docker Swarm. 
Все чувствительные данные помещаются в ENC(). 
Данные будут автоматически расшифровываться по мере необходимости. 

```yaml
server:
  base-path: /vk_bot
```
Так как на сервере запущено основное приложение: My Mood Tracker, бот доступен по пути **/vk_bot**. 
Все запросы по этому пути фильтруются и перенаправляются Nginx-ом на порт, на котором запущен контейнер приложения. 
Эта настройка нужна для корректного отображения пути в сообщениях об ошибках.

```yaml
spring:
  application:
    name: quoting-vk-bot
```
Имя приложения.

## Работа приложения
Работа приложения основана на Callback API от VK. 
Приложение имеет единственный контроллер и метод по ссылке **/callback**:
```java
    @PostMapping
    public Mono<ResponseEntity<String>> handleEvent(@RequestBody CallbackEvent callbackEvent) {
        log.info("Getting callback event: {}", callbackEvent);
        final String callbackType = callbackEvent.getType();
        if (CONFIRMATION.equals(callbackType)) {
            return confirm(callbackEvent);
        } else if (MESSAGE_NEW.equals(callbackType)) {
            return sendQuotingMessage(callbackEvent);
        }
        throw new NotImplementedException("Bot cannot process this type of request", BASE_URI);
    }
```
Метод обрабатывает два типа запросов:

1. **Подтверждение адреса сервера** - обязательный метод для подключения к Callback API.

За работу данного метода отвечает класс **AddressConfirmationServiceImpl**:
```java
@Service
@Slf4j
public class AddressConfirmationServiceImpl implements AddressConfirmationService {
    private final ConfirmationCode code;
    private final GroupId appGroupId;

    public AddressConfirmationServiceImpl(
            @Value("${vk.confirmation.code}") String code,
            @Value("${vk.confirmation.group_id}") Integer groupId
    ) {
        this.code = new ConfirmationCode(code);
        this.appGroupId = new GroupId(groupId);
    }

    @Override
    public Mono<ConfirmationCode> confirm(GroupId groupId) {
        if (groupId.equals(appGroupId)) {
            log.info("Success vk group confirmation: {}", groupId);
            return Mono.just(code);
        }
        log.error("Failed vk group confirmation: {}", groupId);
        return Mono.error(new AddressConfirmationException("Incorrect group id"));
    }
}
```
Класс проверяет правильность запроса и возвращает специальный код.

**Пример запроса:**
```http request
POST https://moodtracker.ru/vk_bot/callback
Content-Type: application/json

{
  "type": "confirmation",
  "group_id": 226335516
}
```

**Пример ответа:**
```http request
POST https://moodtracker.ru/vk_bot/callback

HTTP/1.1 200 OK
Server: nginx/1.18.0 (Ubuntu)
Date: Thu, 27 Jun 2024 23:48:08 GMT
Content-Type: text/plain;charset=UTF-8
Content-Length: 8
Connection: keep-alive

[SECRET_CODE]
```
**Пример ошибки:**
```http request
HTTP/1.1 400 Bad Request
Server: nginx/1.18.0 (Ubuntu)
Date: Thu, 27 Jun 2024 23:48:45 GMT
Content-Type: application/json
Content-Length: 124
Connection: keep-alive

{
  "timestamp": "2024-06-27T23:48:45.334+00:00",
  "path": "/callback",
  "status": 400,
  "error": "Bad Request",
  "requestId": "43d6d210-2"
}
```
Эта ошибка возникает, если был отправлен некорректный номер группы и выброшено **AddressConfirmationException**.


2. **Новое сообщением пользователя** - запрос типа _NEW_MESSAGE_ с самим сообщением в параметре object.

За работу данного функционала отвечает класс **AddressConfirmationServiceImpl:**
```java
@Service
@Slf4j
@RequiredArgsConstructor
public class SendVkMessageServiceImpl implements SendVkMessageService {
    private final VkMessageAnswerUriBuilder messageAnswerBuilder;

    @Override
    public Mono<Void> send(UserMessageDetails userMessageDetails) {
        return createVkMessageWebClient().post()
                .body(messageAnswerBuilder.buildRequestWithParams(userMessageDetails))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> log.info("Message sent successfully: {}", userMessageDetails))
                .doOnError(e -> log.error("Error during message sending: {}", userMessageDetails, e));
    }

    private WebClient createVkMessageWebClient() {
        return WebClient.builder()
                .baseUrl(SEND_METHOD)
                .defaultHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
    }
}
```
Класс, используя WebClient от WebFlux, отправляет сообщение с цитатой.

**Пример запроса:**
```http request
POST https://moodtracker.ru/vk_bot/callback
Content-Type: application/json

{
  "type": "message_new",
  "object": {
    "message": {
      "date": 1719385094,
      "from_id": 154640822,
      "id": 32,
      "out": 0,
      "version": 10000063,
      "attachments": [],
      "conversation_message_id": 32,
      "fwd_messages": [],
      "important": false,
      "is_hidden": false,
      "peer_id": 154640822,
      "random_id": 0,
      "text": "Ого..."
    },
    "client_info": {
      "button_actions": [
        "text",
        "vkpay",
        "open_app",
        "location",
        "open_link",
        "callback",
        "intent_subscribe",
        "intent_unsubscribe"
      ],
      "keyboard": true,
      "inline_keyboard": true,
      "carousel": true,
      "lang_id": 0
    }
  },
  "groupId": 226335516
}
```
Данный запрос представляет собой сообщение пользователя в чате.

**Пример ответа:**
```http request
HTTP/1.1 200 OK
Server: nginx/1.18.0 (Ubuntu)
Date: Thu, 27 Jun 2024 23:51:50 GMT
Content-Type: text/plain;charset=UTF-8
Content-Length: 2
Connection: keep-alive

ok
```
Это подтверждает, что VK получил сообщение. Далее отправляется сообщение пользователю с его цитатой:

```http request
POST https://api.vk.com/method/messages.send
Content-Type: application/x-www-form-urlencoded

user_id={{user_id}}
random_id={{random_id}}
message={{message}}
v={{api_version}}
access_token={{access_token}}
```

Если на метод контроллера пришло событие неизвестного типа, то возвращается 501 NOT_IMPLEMENTED