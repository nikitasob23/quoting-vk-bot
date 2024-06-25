package com.niksob.quoting_vk_bot.exception.vk.group.confirmation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceException extends RuntimeException {
    protected String resource;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
