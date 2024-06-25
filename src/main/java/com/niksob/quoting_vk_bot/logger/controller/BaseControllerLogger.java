package com.niksob.quoting_vk_bot.logger.controller;

import org.springframework.http.HttpStatus;

public interface BaseControllerLogger {
    void info(HttpStatus httpStatus);

    void error(String path, HttpStatus httpStatus, Throwable e);
}
