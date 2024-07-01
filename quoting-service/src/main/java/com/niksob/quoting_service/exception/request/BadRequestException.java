package com.niksob.quoting_service.exception.request;

import com.niksob.quoting_service.exception.ResourceException;

public class BadRequestException extends ResourceException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String resource) {
        super(message, resource);
    }
}
