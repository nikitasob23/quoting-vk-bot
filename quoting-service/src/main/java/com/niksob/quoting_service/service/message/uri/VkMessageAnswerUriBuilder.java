package com.niksob.quoting_service.service.message.uri;


import com.niksob.quoting_service.model.message.UserMessageDetails;
import org.springframework.web.reactive.function.BodyInserters;

public interface VkMessageAnswerUriBuilder {
    BodyInserters.FormInserter<String> buildRequestWithParams(UserMessageDetails userMessageDetails);
}
