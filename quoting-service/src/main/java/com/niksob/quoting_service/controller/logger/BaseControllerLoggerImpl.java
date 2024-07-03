package com.niksob.quoting_service.controller.logger;

import com.niksob.quoting_service.exception.ResourceException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public class BaseControllerLoggerImpl implements BaseControllerLogger {
    private final Logger log;

    @Value("${server.base-path:}")
    private String baseAppPath;

    @Override
    public void info(HttpStatus httpStatus) {
        log.info("Controller return success https status: {}", httpStatus);
    }

    @Override
    public void error(String path, HttpStatus httpStatus, Throwable e) {
        if (e instanceof ResourceException resourceException) {
            resourceException.setResource(baseAppPath + path);
        }
        log.error("Controller return failed https status: {}", httpStatus, e);
    }
}
