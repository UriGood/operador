package com.unir.operador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CompraNotFoundException extends RuntimeException {
    public CompraNotFoundException(String message) {
        super(message);
    }
}