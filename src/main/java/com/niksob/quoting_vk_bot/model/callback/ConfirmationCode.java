package com.niksob.quoting_vk_bot.model.callback;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmationCode {
    private final String value;
}
