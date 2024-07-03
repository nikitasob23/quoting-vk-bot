package com.niksob.quoting_service.service.message;

import com.niksob.quoting_service.model.message.UserMessageDetails;
import com.niksob.quoting_service.service.message.uri.VkMessageAnswerUriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SendVkMessageServiceImpl implements SendVkMessageService {
    private final VkMessageAnswerUriBuilder messageAnswerBuilder;

    @Qualifier("vkSendMessageWebClient")
    private final WebClient vkSendMessageWebClient;

    @Override
    public Mono<Void> send(UserMessageDetails userMessageDetails) {
        return vkSendMessageWebClient.post()
                .body(messageAnswerBuilder.buildRequestWithParams(userMessageDetails))
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(response -> log.info("Message sent successfully: {}", userMessageDetails))
                .doOnError(e -> log.error("Error during message sending: {}", userMessageDetails, e));
    }
}
