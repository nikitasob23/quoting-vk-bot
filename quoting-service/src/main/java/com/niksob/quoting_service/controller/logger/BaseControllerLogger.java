package com.niksob.quoting_service.controller.logger;

import org.springframework.http.HttpStatus;

public interface BaseControllerLogger {
    void info(HttpStatus httpStatus);

    void error(String path, HttpStatus httpStatus, Throwable e);
}
