package com.niksob.quoting_vk_bot.service.message.uri;


import com.niksob.quoting_vk_bot.model.message.UserMessageDetails;
import org.springframework.web.reactive.function.BodyInserters;

public interface VkMessageAnswerUriBuilder {
    BodyInserters.FormInserter<String> buildRequestWithParams(UserMessageDetails userMessageDetails);
}
