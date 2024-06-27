package com.niksob.quoting_vk_bot.service.address_confirmation;

import com.niksob.quoting_vk_bot.model.group.GroupId;
import com.niksob.quoting_vk_bot.model.callback.ConfirmationCode;
import reactor.core.publisher.Mono;

public interface AddressConfirmationService {
    Mono<ConfirmationCode> confirm(GroupId groupId);
}
