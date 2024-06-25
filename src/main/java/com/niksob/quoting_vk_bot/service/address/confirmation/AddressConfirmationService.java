package com.niksob.quoting_vk_bot.service.address.confirmation;

import com.niksob.quoting_vk_bot.model.vk.group.confirmation.VkGroupConfirmation;
import com.niksob.quoting_vk_bot.model.vk.group.confirmation.code.ConfirmationCode;
import reactor.core.publisher.Mono;

public interface AddressConfirmationService {
    Mono<ConfirmationCode> confirm(VkGroupConfirmation vkGroupConfirmation);
}
