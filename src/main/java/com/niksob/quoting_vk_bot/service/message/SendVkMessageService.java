package com.niksob.quoting_vk_bot.service.message;

import com.niksob.quoting_vk_bot.model.message.UserMessageDetails;
import reactor.core.publisher.Mono;

public interface SendVkMessageService {
    Mono<Void> send(UserMessageDetails userMessageDetails);
}
