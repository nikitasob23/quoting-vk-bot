package com.niksob.quoting_service.controller.logger;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class BaseControllerLoggerImpl implements BaseControllerLogger {
    private final Logger log;

    @Override
    public void info(HttpStatus httpStatus) {
        log.info("Controller return success https status: {}", httpStatus);
    }

    @Override
    public void error(String path, HttpStatus httpStatus, Throwable e) {
        log.error("Controller return failed https status: {}", httpStatus, e);
    }
}
