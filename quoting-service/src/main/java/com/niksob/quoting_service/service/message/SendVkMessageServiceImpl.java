package com.niksob.quoting_service.service.message;

import com.niksob.quoting_service.model.message.UserMessageDetails;
import com.niksob.quoting_service.service.message.uri.VkMessageAnswerUriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static com.niksob.quoting_service.uri.vk.send.VkUri.SEND_METHOD;

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
