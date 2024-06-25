package com.niksob.quoting_vk_bot.exception.vk.group.confirmation;

public class VkGroupConfirmationException extends ResourceException {
    public VkGroupConfirmationException(String message) {
        super(message);
    }

    public VkGroupConfirmationException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
