package com.niksob.quoting_service.service.message.uri;

import com.niksob.quoting_service.config.vk.VkMessageDetailsConfig;
import com.niksob.quoting_service.model.message.UserMessageDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.UUID;

import static com.niksob.quoting_service.value.MessageSendingKey.*;

@Service
@Slf4j
@AllArgsConstructor
public class VkMessageAnswerUriBuilderImpl implements VkMessageAnswerUriBuilder {
    private final VkMessageDetailsConfig vkMessageDetailsConfig;

    @Override
    public BodyInserters.FormInserter<String> buildRequestWithParams(UserMessageDetails userMessageDetails) {
        final BodyInserters.FormInserter<String> formInserter =
                BodyInserters.fromFormData(USER_ID, String.valueOf(userMessageDetails.getUserId().getValue()))
                        .with(RANDOM_ID, String.valueOf(UUID.randomUUID().hashCode()))
                        .with(MESSAGE, vkMessageDetailsConfig.getMessageTemplate()
                                .formatted(userMessageDetails.getText()))
                        .with(V, vkMessageDetailsConfig.getApiVersion());
        log.info("Attempt to sending message to the user. Response body without access_token: {}", formInserter);

        formInserter.with(ACCESS_TOKEN, vkMessageDetailsConfig.getToken());
        return formInserter;
    }
}
