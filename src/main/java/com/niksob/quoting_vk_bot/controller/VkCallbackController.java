package com.niksob.quoting_vk_bot.controller;

import com.niksob.quoting_vk_bot.controller.logger.BaseControllerLogger;
import com.niksob.quoting_vk_bot.exception.ResourceException;
import com.niksob.quoting_vk_bot.exception.request.NotImplementedException;
import com.niksob.quoting_vk_bot.mapper.callback.CallbackEventMapper;
import com.niksob.quoting_vk_bot.model.callback.CallbackEvent;
import com.niksob.quoting_vk_bot.model.callback.ConfirmationCode;
import com.niksob.quoting_vk_bot.model.group.GroupId;
import com.niksob.quoting_vk_bot.model.message.UserMessageDetails;
import com.niksob.quoting_vk_bot.service.address_confirmation.AddressConfirmationService;
import com.niksob.quoting_vk_bot.service.message.SendVkMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static com.niksob.quoting_vk_bot.uri.controller.CallbackControllerUri.BASE_URI;
import static com.niksob.quoting_vk_bot.value.CallbackEventType.CONFIRMATION;
import static com.niksob.quoting_vk_bot.value.CallbackEventType.MESSAGE_NEW;
import static com.niksob.quoting_vk_bot.value.ResponseBody.OK;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(BASE_URI)
public class VkCallbackController {

    private final AddressConfirmationService addressConfirmationService;
    private final SendVkMessageService sendVkMessageService;

    @Qualifier("callbackControllerLogger")
    private final BaseControllerLogger controllerLogger;

    private final CallbackEventMapper callbackEventMapper;

    @PostMapping
    public Mono<ResponseEntity<String>> handleEvent(@RequestBody CallbackEvent callbackEvent) {
        log.info("Getting callback event: {}", callbackEvent);
        final String callbackType = callbackEvent.getType();
        if (CONFIRMATION.equals(callbackType)) {
            return confirm(callbackEvent);
        } else if (MESSAGE_NEW.equals(callbackType)) {
            return sendQuotingMessage(callbackEvent);
        }
        throw new NotImplementedException("Bot cannot process this type of request", BASE_URI);
    }

    private Mono<ResponseEntity<String>> confirm(CallbackEvent callbackEvent) {
        final GroupId groupId = callbackEventMapper.toGroupId(callbackEvent);
        return addressConfirmationService.confirm(groupId)
                .map(ConfirmationCode::getValue)
                .map(confirmCode -> ResponseEntity.status(HttpStatus.OK).body(confirmCode))
                .doOnSuccess(code -> controllerLogger.info(HttpStatus.OK))
                .doOnError(this::setResourceUri);
    }

    private Mono<ResponseEntity<String>> sendQuotingMessage(CallbackEvent callbackEvent) {
        return Mono.just(ResponseEntity.status(HttpStatus.OK).body(OK))
                .doOnSuccess(response -> {
                    final UserMessageDetails messageDetails = callbackEventMapper.toUserMessageDetails(callbackEvent);
                    sendQuotingMessage(messageDetails);
                })
                .doOnSuccess(okResponse -> log.info("Controller return success https status: {}", okResponse))
                .doOnError(this::setResourceUri);
    }

    private void sendQuotingMessage(UserMessageDetails userMessageDetails) {
        sendVkMessageService.send(userMessageDetails)
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(code -> controllerLogger.info(HttpStatus.OK))
                .doOnError(e -> controllerLogger.error(BASE_URI, HttpStatus.BAD_REQUEST, e))
                .subscribe();
    }

    private void setResourceUri(Throwable e) {
        ((ResourceException) e).setResource(BASE_URI);
    }
}