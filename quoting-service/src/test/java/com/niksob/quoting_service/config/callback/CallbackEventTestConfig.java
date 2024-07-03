package com.niksob.quoting_service.config.callback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niksob.quoting_service.model.callback.CallbackEvent;
import com.niksob.quoting_service.model.message.Message;
import com.niksob.quoting_service.model.message.UserMessageDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Map;

import static com.niksob.quoting_service.value.CallbackEventType.CONFIRMATION;
import static com.niksob.quoting_service.value.CallbackEventType.MESSAGE_NEW;

@TestConfiguration
@RequiredArgsConstructor
public class CallbackEventTestConfig {
    private final UserMessageDetails userMessageDetails;

    private final ObjectMapper mapper;

    @Value("${vk.confirmation.group_id}")
    private Integer groupId;

    @Bean("defCallbackEventJson")
    public String getDefTestCallbackEvent() throws JsonProcessingException {
        final CallbackEvent defTestCallbackEvent = new CallbackEvent(
                MESSAGE_NEW,
                Map.of("message",
                        Message.builder()
                                .peerId(userMessageDetails.getUserId().getValue())
                                .text(userMessageDetails.getText())
                                .build()
                ),
                groupId
        );
        return mapper.writeValueAsString(defTestCallbackEvent);
    }

    @Bean("callbackEventWithMissingField")
    public String getCallbackEventWithMissingField() throws JsonProcessingException {
        final CallbackEvent callbackEvent = new CallbackEvent(MESSAGE_NEW, null, null);
        return mapper.writeValueAsString(callbackEvent);
    }

    @Bean("confirmCallbackEventJson")
    public String getTestConfirmCallbackEvent() throws JsonProcessingException {
        final CallbackEvent testConfirmCallbackEvent = new CallbackEvent(CONFIRMATION, null, groupId);
        return mapper.writeValueAsString(testConfirmCallbackEvent);
    }

    @Bean("unknownCallbackEventJson")
    public String getUnknownCallbackEventJson() throws JsonProcessingException {
        final CallbackEvent callbackEvent = new CallbackEvent("unknown_event", null, groupId);
        return mapper.writeValueAsString(callbackEvent);
    }
}
