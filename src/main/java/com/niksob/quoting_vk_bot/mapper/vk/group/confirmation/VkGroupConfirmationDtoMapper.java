package com.niksob.quoting_vk_bot.mapper.vk.group.confirmation;

import com.niksob.quoting_vk_bot.dto.vk.group.confirmation.VkGroupConfirmationDto;
import com.niksob.quoting_vk_bot.model.vk.group.confirmation.VkGroupConfirmation;
import com.niksob.quoting_vk_bot.model.vk.group.confirmation.code.ConfirmationCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VkGroupConfirmationDtoMapper {
    VkGroupConfirmation fromDto(VkGroupConfirmationDto dto);

    default String toDto(ConfirmationCode code) {
        return code.getValue();
    }
}
