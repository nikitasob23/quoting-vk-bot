package com.niksob.quoting_service.model.message;

import com.niksob.quoting_service.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMessageDetails {
    private final UserId userId;
    private final String text;
}
