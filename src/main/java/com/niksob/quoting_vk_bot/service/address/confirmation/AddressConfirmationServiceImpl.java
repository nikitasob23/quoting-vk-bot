package com.niksob.quoting_vk_bot.service.address.confirmation;

import com.niksob.quoting_vk_bot.exception.vk.group.confirmation.VkGroupConfirmationException;
import com.niksob.quoting_vk_bot.model.vk.group.confirmation.VkGroupConfirmation;
import com.niksob.quoting_vk_bot.model.vk.group.confirmation.code.ConfirmationCode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@AllArgsConstructor
public class AddressConfirmationServiceImpl implements AddressConfirmationService {
    private final VkGroupConfirmation defVkGroupConfirmation;
    private final ConfirmationCode confirmationCode;

    @Override
    public Mono<ConfirmationCode> confirm(VkGroupConfirmation vkGroupConfirmation) {
        if (vkGroupConfirmation.equals(defVkGroupConfirmation)) {
            log.info("Success vk group confirmation: {}", vkGroupConfirmation);
            return Mono.just(confirmationCode);
        }
        log.error("Failed vk group confirmation: {}", vkGroupConfirmation);
        return Mono.error(new VkGroupConfirmationException("Incorrect group id or status"));
    }
}
