package com.niksob.quoting_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niksob.quoting_service.QuotingVkBotApplication;
import com.niksob.quoting_service.config.callback.CallbackEventTestConfig;
import com.niksob.quoting_service.config.message.UserMessageDetailsTestConfig;
import com.niksob.quoting_service.config.message.send.VkSendMessageWebClientTestConfig;
import com.niksob.quoting_service.config.vk.VkMessageDetailsConfig;
import com.niksob.quoting_service.model.error.ErrorDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.niksob.quoting_service.uri.controller.CallbackControllerUri.BASE_URI;
import static com.niksob.quoting_service.value.ResponseBody.OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {
        QuotingVkBotApplication.class,
        VkMessageDetailsConfig.class,
        UserMessageDetailsTestConfig.class,
        CallbackEventTestConfig.class,
        VkSendMessageWebClientTestConfig.class
})
@ActiveProfiles("test")
@Slf4j
public class VkCallbackControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @Value("${server.base-path:}")
    private String basePath;
    @Value("${vk.confirmation.code}")
    private String code;

    @Autowired
    @Qualifier("defCallbackEventJson")
    private String defCallbackEventJson;
    @Autowired
    @Qualifier("confirmCallbackEventJson")
    private String confirmCallbackEventJson;
    @Autowired
    @Qualifier("unknownCallbackEventJson")
    private String unknownCallbackEventJson;
    @Autowired
    @Qualifier("callbackEventWithMissingField")
    private String callbackEventWithMissingField;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testConfirmationEvent() {
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(confirmCallbackEventJson)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(code);
    }

    @Test
    public void testMessageNewEvent() {
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(defCallbackEventJson)
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .isEqualTo(OK);
    }

    @Test
    public void testUnknownEventType() {
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(unknownCallbackEventJson)
                .exchange()
                .expectStatus().isEqualTo(501)
                .expectBody(String.class)
                .value(errorDetailsJson -> {
                    try {
                        final ErrorDetails errorDetails = mapper.readValue(errorDetailsJson, ErrorDetails.class);
                        assertThat(errorDetails.getTimestamp()).isNotNull();
                        assertThat(errorDetails.getStatus()).isEqualTo(501);
                        assertThat(errorDetails.getError()).isEqualTo("Not Implemented");
                        assertThat(errorDetails.getMessage())
                                .isEqualTo("Bot cannot process this type of request");
                        assertThat(errorDetails.getPath()).isEqualTo(basePath + BASE_URI);
                    } catch (JsonProcessingException e) {
                        logJsonProcessingException(e);
                    }
                });

    }

    @Test
    public void testInvalidJsonFormat() {
        final String invalidJson = defCallbackEventJson.substring(0, defCallbackEventJson.length() - 1); // Missing closing brace

        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(invalidJson)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorDetails -> {
                    try {
                        ErrorDetails details = mapper.readValue(errorDetails, ErrorDetails.class);
                        assertThat(details.getStatus()).isEqualTo(400);
                        assertThat(details.getError()).isEqualTo("Bad Request");
                        assertThat(details.getMessage()).contains("Failed to read HTTP message");
                    } catch (JsonProcessingException e) {
                        logJsonProcessingException(e);
                    }
                });
    }

    @Test
    public void testMissingRequiredField() {
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(callbackEventWithMissingField)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorDetails -> {
                    try {
                        ErrorDetails details = mapper.readValue(errorDetails, ErrorDetails.class);
                        assertThat(details.getStatus()).isEqualTo(400);
                        assertThat(details.getError()).isEqualTo("Bad Request");
                        assertThat(details.getMessage())
                                .contains("Illegal state of callback event: missing or incorrect object field");
                    } catch (JsonProcessingException e) {
                        logJsonProcessingException(e);
                    }
                });
    }

    @Test
    public void testEmptyRequestBody() {
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue("")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .value(errorDetails -> {
                    try {
                        ErrorDetails details = mapper.readValue(errorDetails, ErrorDetails.class);
                        assertThat(details.getStatus()).isEqualTo(400);
                        assertThat(details.getError()).isEqualTo("Bad Request");
                        assertThat(details.getMessage()).contains("No request body");
                    } catch (JsonProcessingException e) {
                        logJsonProcessingException(e);
                    }
                });
    }

    @Test
    public void testIncorrectContentType() {
        webTestClient.post()
                .uri(BASE_URI)
                .contentType(MediaType.TEXT_PLAIN)
                .bodyValue(defCallbackEventJson)
                .exchange()
                .expectStatus().isEqualTo(415) // Unsupported Media Type
                .expectBody(String.class)
                .value(errorDetails -> {
                    try {
                        ErrorDetails details = mapper.readValue(errorDetails, ErrorDetails.class);
                        assertThat(details.getStatus()).isEqualTo(415);
                        assertThat(details.getError()).isEqualTo("Unsupported Media Type");
                        assertThat(details.getMessage()).contains("Content type 'text/plain' not supported");
                    } catch (JsonProcessingException e) {
                        logJsonProcessingException(e);
                    }
                });
    }

    @Test
    public void testUnknownUri() {
        webTestClient.post()
                .uri("/unknown")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(defCallbackEventJson)
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class)
                .value(errorDetails -> {
                    try {
                        ErrorDetails details = mapper.readValue(errorDetails, ErrorDetails.class);
                        assertThat(details.getStatus()).isEqualTo(404);
                        assertThat(details.getError()).isEqualTo("Not Found");
                        assertThat(details.getMessage()).contains("No static resource unknown");
                    } catch (JsonProcessingException e) {
                        logJsonProcessingException(e);
                    }
                });
    }

    private static void logJsonProcessingException(JsonProcessingException e) {
        log.error("Error parsing JSON response", e);
    }
}