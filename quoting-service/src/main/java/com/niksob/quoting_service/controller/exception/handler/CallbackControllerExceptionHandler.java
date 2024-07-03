package com.niksob.quoting_service.controller.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.niksob.quoting_service.controller.logger.BaseControllerLogger;
import com.niksob.quoting_service.exception.ResourceException;
import com.niksob.quoting_service.exception.request.BadRequestException;
import com.niksob.quoting_service.exception.request.InternalServerError;
import com.niksob.quoting_service.exception.request.NotImplementedException;
import com.niksob.quoting_service.exception.address_confirmation.AddressConfirmationException;
import com.niksob.quoting_service.model.error.ErrorDetails;
import com.niksob.quoting_service.util.date.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ServerWebInputException;

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

    @ExceptionHandler(ServerWebInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDetails> handleServerWebInputException(ServerWebInputException ex, ServerHttpRequest request) {
        final ErrorDetails errorDetails = new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getReason(),
                request.getURI().getPath()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDetails handleJsonProcessingException(JsonProcessingException ex, ServerHttpRequest request) {
        return new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getOriginalMessage(),
                request.getURI().getPath()
        );
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
