package com.unir.ms_books_catalogue.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String error;
    private int status;
    private String timestamp;
}
