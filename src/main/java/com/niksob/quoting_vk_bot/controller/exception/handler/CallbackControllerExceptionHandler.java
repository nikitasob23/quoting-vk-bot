package com.niksob.quoting_vk_bot.controller.exception.handler;

import com.niksob.quoting_vk_bot.exception.ResourceException;
import com.niksob.quoting_vk_bot.exception.request.BadRequestException;
import com.niksob.quoting_vk_bot.exception.address_confirmation.AddressConfirmationException;
import com.niksob.quoting_vk_bot.model.error.ErrorDetails;
import com.niksob.quoting_vk_bot.util.date.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class CallbackControllerExceptionHandler {
    @ExceptionHandler(AddressConfirmationException.class)
    public ResponseEntity<ErrorDetails> handleResponseStatusException(AddressConfirmationException e) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorDetails errorDetails = createErrorDetails(e, httpStatus);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException e) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorDetails errorDetails = createErrorDetails(e, httpStatus);
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }

    private ErrorDetails createErrorDetails(ResourceException e, HttpStatus httpStatus) {
        return new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                e.getResource()
        );
    }
}
