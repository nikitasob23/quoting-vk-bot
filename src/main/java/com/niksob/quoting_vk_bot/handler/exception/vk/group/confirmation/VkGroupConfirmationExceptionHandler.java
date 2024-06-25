package com.niksob.quoting_vk_bot.handler.exception.vk.group.confirmation;

import com.niksob.quoting_vk_bot.exception.vk.group.confirmation.VkGroupConfirmationException;
import com.niksob.quoting_vk_bot.model.rest.response.error.ErrorDetails;
import com.niksob.quoting_vk_bot.util.date.DateTimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class VkGroupConfirmationExceptionHandler {
    @ExceptionHandler(VkGroupConfirmationException.class)
    public ResponseEntity<ErrorDetails> handleResponseStatusException(VkGroupConfirmationException e) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorDetails errorDetails = new ErrorDetails(
                DateTimeUtil.getTimestamp(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                e.getMessage(),
                e.getResource()
        );
        return ResponseEntity.status(httpStatus).body(errorDetails);
    }
}
