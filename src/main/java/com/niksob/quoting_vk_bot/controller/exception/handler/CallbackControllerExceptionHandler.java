package com.niksob.quoting_vk_bot.controller.exception.handler;

import com.niksob.quoting_vk_bot.controller.logger.BaseControllerLogger;
import com.niksob.quoting_vk_bot.exception.ResourceException;
import com.niksob.quoting_vk_bot.exception.request.BadRequestException;
import com.niksob.quoting_vk_bot.exception.request.InternalServerError;
import com.niksob.quoting_vk_bot.exception.request.NotImplementedException;
import com.niksob.quoting_vk_bot.exception.address_confirmation.AddressConfirmationException;
import com.niksob.quoting_vk_bot.model.error.ErrorDetails;
import com.niksob.quoting_vk_bot.util.date.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class CallbackControllerExceptionHandler {
    @Qualifier("callbackControllerLogger")
    private final BaseControllerLogger controllerLogger;

    @ExceptionHandler(AddressConfirmationException.class)
    public ResponseEntity<ErrorDetails> handleResponseStatusException(AddressConfirmationException e) {
        return createErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException e) {
        return createErrorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(NotImplementedException e) {
        return createErrorResponse(e, HttpStatus.NOT_IMPLEMENTED);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorDetails> handleInternalServerError(InternalServerError e) {
        return createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceException.class)
    public ResponseEntity<ErrorDetails> handleResourceException(ResourceException e) {
        return createErrorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception e) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final ErrorDetails errorDetails = new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                null
        );
        controllerLogger.error(null, httpStatus, e);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }

    private ResponseEntity<ErrorDetails> createErrorResponse(ResourceException e, HttpStatus httpStatus) {
        final ErrorDetails errorDetails = new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                e.getResource()
        );
        controllerLogger.error(e.getResource(), httpStatus, e);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }
}
