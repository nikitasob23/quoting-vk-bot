package com.niksob.quoting_service.exception.request;

import com.niksob.quoting_service.exception.ResourceException;

public class NotImplementedException extends ResourceException {
    public NotImplementedException(String message) {
        super(message);
    }

    public NotImplementedException(String message, String resource) {
        super(message, resource);
    }
}
