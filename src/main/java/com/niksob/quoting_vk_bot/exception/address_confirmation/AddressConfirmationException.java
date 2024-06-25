package com.niksob.quoting_vk_bot.exception.address_confirmation;

import com.niksob.quoting_vk_bot.exception.ResourceException;

public class AddressConfirmationException extends ResourceException {
    public AddressConfirmationException(String message) {
        super(message);
    }

    public AddressConfirmationException(String message, String resource) {
        super(message);
        this.resource = resource;
    }
}
