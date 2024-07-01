package com.niksob.quoting_service.exception;

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

    public ResourceException(String resource, Throwable cause) {
        super(cause);
        this.resource = resource;
    }
}
