package com.niksob.quoting_service.service.address_confirmation;

import com.niksob.quoting_service.model.group.GroupId;
import com.niksob.quoting_service.model.callback.ConfirmationCode;
import reactor.core.publisher.Mono;

public interface AddressConfirmationService {
    Mono<ConfirmationCode> confirm(GroupId groupId);
}
