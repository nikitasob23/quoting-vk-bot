package com.niksob.quoting_service.controller.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.niksob.quoting_service.controller.logger.BaseControllerLogger;
import com.niksob.quoting_service.exception.request.BadRequestException;
import com.niksob.quoting_service.exception.request.InternalServerError;
import com.niksob.quoting_service.exception.request.NotImplementedException;
import com.niksob.quoting_service.exception.address_confirmation.AddressConfirmationException;
import com.niksob.quoting_service.model.error.ErrorDetails;
import com.niksob.quoting_service.util.date.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

@RestControllerAdvice
@RequiredArgsConstructor
public class CallbackControllerExceptionHandler {
    @Qualifier("callbackControllerLogger")
    private final BaseControllerLogger controllerLogger;

    @Value("${server.base-path}")
    private String basePath;

    @ExceptionHandler(AddressConfirmationException.class)
    public ResponseEntity<ErrorDetails> handleResponseStatusException(
            AddressConfirmationException e, ServerHttpRequest request
    ) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, request, e);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException e, ServerHttpRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, request, e);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(
            NotImplementedException e, ServerHttpRequest request
    ) {
        return createErrorResponse(HttpStatus.NOT_IMPLEMENTED, request, e);
    }

    @ExceptionHandler(ServerWebInputException.class)
    public ResponseEntity<ErrorDetails> handleServerWebInputException(
            ServerWebInputException e, ServerHttpRequest request
    ) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, request, e);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorDetails> handleJsonProcessingException(
            JsonProcessingException e, ServerHttpRequest request
    ) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, request, e);
    }

    @ExceptionHandler(UnsupportedMediaTypeStatusException.class)
    public ResponseEntity<ErrorDetails> handleUnsupportedMediaTypeStatusException(
            UnsupportedMediaTypeStatusException e, ServerHttpRequest request
    ) {
        return createErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, request, e);
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorDetails> handleInternalServerError(InternalServerError e, ServerHttpRequest request) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, e);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorDetails> handleNoResourceFoundException(
            NoResourceFoundException e, ServerHttpRequest request
    ) {
        return createErrorResponse(HttpStatus.NOT_FOUND, request, e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGenericException(Exception e, ServerHttpRequest request) {
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, request, e);
    }

    private ResponseEntity<ErrorDetails> createErrorResponse(
            HttpStatus httpStatus, ServerHttpRequest request, Exception e
    ) {
        final String path = request.getURI().getPath();
        final ErrorDetails errorDetails = new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                basePath + path
        );
        controllerLogger.error(path, httpStatus, e);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }
}
