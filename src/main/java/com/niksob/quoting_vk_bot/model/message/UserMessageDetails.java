package com.niksob.quoting_vk_bot.model.message;

import com.niksob.quoting_vk_bot.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMessageDetails {
    private final UserId userId;
    private final String text;
}
