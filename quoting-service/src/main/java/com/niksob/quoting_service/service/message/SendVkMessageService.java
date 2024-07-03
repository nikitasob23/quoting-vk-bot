package com.niksob.quoting_service.service.message;

import com.niksob.quoting_service.model.message.UserMessageDetails;
import reactor.core.publisher.Mono;

public interface SendVkMessageService {
    Mono<Void> send(UserMessageDetails userMessageDetails);
}
