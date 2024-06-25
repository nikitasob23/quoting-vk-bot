package com.niksob.quoting_vk_bot.model.vk.group.confirmation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VkGroupConfirmation {
    private final String type;
    private final Long groupId;
}
