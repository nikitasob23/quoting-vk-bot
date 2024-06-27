package com.niksob.quoting_vk_bot.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfo {
    @JsonProperty("button_actions")
    private List<String> buttonActions;
    private Boolean keyboard;
    @JsonProperty("inline_keyboard")
    private Boolean inlineKeyboard;
    private Boolean carousel;
    private Integer langId;
}
