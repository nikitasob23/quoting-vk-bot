package com.niksob.quoting_vk_bot.controller.address.confirmation;

import com.niksob.quoting_vk_bot.dto.vk.group.confirmation.VkGroupConfirmationDto;
import com.niksob.quoting_vk_bot.logger.controller.BaseControllerLogger;
import com.niksob.quoting_vk_bot.mapper.vk.group.confirmation.VkGroupConfirmationDtoMapper;
import com.niksob.quoting_vk_bot.model.vk.group.confirmation.VkGroupConfirmation;
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

import static com.niksob.quoting_vk_bot.uri.controller.AddressConfirmationControllerUri.BASE_URI;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(BASE_URI)
public class AddressConfirmationController {
    private final AddressConfirmationService addressConfirmationService;

    private final VkGroupConfirmationDtoMapper vkGroupConfirmationDtoMapper;

    @Qualifier("addressConfirmationControllerLogger")
    private final BaseControllerLogger controllerLogger;

    @PostMapping
    public Mono<String> confirm(@RequestBody VkGroupConfirmationDto vkGroupConfirmationDto) {
        final VkGroupConfirmation vkGroupConfirmation = vkGroupConfirmationDtoMapper.fromDto(vkGroupConfirmationDto);
        return addressConfirmationService.confirm(vkGroupConfirmation)
                .map(vkGroupConfirmationDtoMapper::toDto)
                .doOnSuccess(code -> controllerLogger.info(HttpStatus.OK))
                .doOnError(e -> controllerLogger.error(BASE_URI, HttpStatus.BAD_REQUEST, e));
    }
}
