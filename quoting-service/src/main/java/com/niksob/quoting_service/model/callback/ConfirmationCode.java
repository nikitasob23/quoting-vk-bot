package com.niksob.quoting_service.model.callback;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConfirmationCode {
    private final String value;
}
