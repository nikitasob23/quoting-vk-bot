package com.niksob.quoting_vk_bot.logger.controller;

import com.niksob.quoting_vk_bot.exception.vk.group.confirmation.ResourceException;
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
        ((ResourceException) e).setResource(baseAppPath + path);
        log.error("Controller return failed https status: {}", httpStatus, e);
    }
}
