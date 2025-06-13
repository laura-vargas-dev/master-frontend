package com.unir.ms_books_catalogue.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.Instant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid request format: " + ex.getMessage();
        String errorType = "BAD_REQUEST";
        ErrorResponse error = new ErrorResponse(
            errorMessage, 
            errorType,
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "Invalid parameter type: " + ex.getMessage();
        String errorType = "BAD_REQUEST";
        ErrorResponse error = new ErrorResponse(
            errorMessage, 
            errorType,
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        String errorMessage = "An error occurred: " + ex.getMessage();
        String errorType = "INTERNAL_SERVER_ERROR";
        ErrorResponse error = new ErrorResponse(
            errorMessage, 
            errorType,
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Instant.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({JsonProcessingException.class, JsonMappingException.class})
    public ResponseEntity<ErrorResponse> handleJsonException(Exception ex) {
        String errorMessage = "Invalid JSON format: " + ex.getMessage();
        String errorType = "BAD_REQUEST";
        ErrorResponse error = new ErrorResponse(
            errorMessage,
            errorType,
            HttpStatus.BAD_REQUEST.value(),
            Instant.now().toString()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
