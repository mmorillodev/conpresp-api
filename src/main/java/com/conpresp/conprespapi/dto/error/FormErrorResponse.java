package com.conpresp.conprespapi.dto.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormErrorResponse {
    private final String field;
    private final String error;
}
