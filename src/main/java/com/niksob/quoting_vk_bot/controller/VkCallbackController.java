package com.niksob.quoting_vk_bot.controller;

import com.niksob.quoting_vk_bot.controller.logger.BaseControllerLogger;
import com.niksob.quoting_vk_bot.exception.request.BadRequestException;
import com.niksob.quoting_vk_bot.mapper.callback.CallbackEventMapper;
import com.niksob.quoting_vk_bot.model.callback.CallbackEvent;
import com.niksob.quoting_vk_bot.model.callback.ConfirmationCode;
import com.niksob.quoting_vk_bot.model.group.GroupId;
import com.niksob.quoting_vk_bot.service.address.confirmation.AddressConfirmationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import static com.niksob.quoting_vk_bot.uri.controller.CallbackControllerUri.BASE_URI;
import static com.niksob.quoting_vk_bot.value.CallbackEventType.CONFIRMATION;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(BASE_URI)
public class VkCallbackController {

    private final AddressConfirmationService addressConfirmationService;

    @Qualifier("callbackControllerLogger")
    private final BaseControllerLogger controllerLogger;

    private final CallbackEventMapper callbackEventMapper;

    @PostMapping
    public Mono<String> confirm(@RequestBody CallbackEvent callbackEvent) {
        final String callbackType = callbackEvent.getType();
        if (CONFIRMATION.getValue().equals(callbackType)) {
            final GroupId groupId = callbackEventMapper.toGroupId(callbackEvent);
            return addressConfirmationService.confirm(groupId)
                    .map(ConfirmationCode::getValue)
                    .doOnSuccess(code -> controllerLogger.info(HttpStatus.OK))
                    .doOnError(e -> controllerLogger.error(BASE_URI, HttpStatus.BAD_REQUEST, e));
        }
        throw new BadRequestException("Not allowed");
    }
}
