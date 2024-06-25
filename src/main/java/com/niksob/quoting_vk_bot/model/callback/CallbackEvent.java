package com.niksob.quoting_vk_bot.model.callback;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CallbackEvent {
    private String type;
    private Object object;
    @JsonProperty("group_id")
    private Integer groupId;
}
