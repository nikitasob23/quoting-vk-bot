package com.niksob.quoting_vk_bot.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CallbackEventType {
    CONFIRMATION("confirmation"),
    MESSAGE_NEW("message_new");

    private final String value;
}
