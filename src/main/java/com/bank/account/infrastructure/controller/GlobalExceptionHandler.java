package com.bank.account.infrastructure.controller;

import com.bank.account.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT) // o BAD_REQUEST si prefieres
                .body(Map.of(
                        "timestamp", Instant.now(),
                        "status", HttpStatus.CONFLICT.value(),
                        "error", ex.getMessage()
                ));
    }
}