package com.niksob.quoting_vk_bot.model.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CallbackEvent {
    private String type;
    private Object object;
    @JsonProperty("group_id")
    private Integer groupId;
}
