package com.niksob.quoting_vk_bot.service.address_confirmation;

import com.niksob.quoting_vk_bot.exception.address_confirmation.AddressConfirmationException;
import com.niksob.quoting_vk_bot.model.group.GroupId;
import com.niksob.quoting_vk_bot.model.callback.ConfirmationCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AddressConfirmationServiceImpl implements AddressConfirmationService {
    private final ConfirmationCode code;
    private final GroupId appGroupId;

    public AddressConfirmationServiceImpl(
            @Value("${vk.confirmation.code}") String code,
            @Value("${vk.confirmation.group_id}") Integer groupId
    ) {
        this.code = new ConfirmationCode(code);
        this.appGroupId = new GroupId(groupId);
    }

    @Override
    public Mono<ConfirmationCode> confirm(GroupId groupId) {
        if (groupId.equals(appGroupId)) {
            log.info("Success vk group confirmation: {}", groupId);
            return Mono.just(code);
        }
        log.error("Failed vk group confirmation: {}", groupId);
        return Mono.error(new AddressConfirmationException("Incorrect group id"));
    }
}
