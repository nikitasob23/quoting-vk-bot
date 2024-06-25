package com.niksob.quoting_vk_bot.dto.vk.group.confirmation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VkGroupConfirmationDto {
    private String type;
    @JsonProperty("group_id")
    private Long groupId;
}
