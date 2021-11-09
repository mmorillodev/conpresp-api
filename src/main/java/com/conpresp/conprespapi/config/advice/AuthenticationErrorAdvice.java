package com.conpresp.conprespapi.config.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthenticationErrorAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public Map<String, String> handle(BadCredentialsException e) {
        return new HashMap<>(){{
            put("cause", e.getMessage());
        }};
    }
}
