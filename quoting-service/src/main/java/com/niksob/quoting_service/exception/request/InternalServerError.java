package com.niksob.quoting_service.exception.request;

import com.niksob.quoting_service.exception.ResourceException;

public class InternalServerError extends ResourceException {
    public InternalServerError(String message) {
        super(message);
    }

    public InternalServerError(String message, String resource) {
        super(message, resource);
    }
}
