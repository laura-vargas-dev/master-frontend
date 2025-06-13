package com.unir.ms_books_payments.controller.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    @NotNull(message = "`purchaseDate` cannot be null")
    @Past(message = "`purchaseDate` must be a past date")
    private Date purchaseDate;

    @NotNull(message = "`totalAmount` cannot be null")
    @Positive(message = "`totalAmount` must be a positive number")
    private Double totalAmount;

    @NotNull(message = "`books` cannot be null")
    @NotEmpty(message = "`books` cannot be empty")
    private List<String> books;

    @NotNull(message = "`customerName` cannot be null")
    @NotEmpty(message = "`customerName` cannot be empty")
    private String customerName;

    @NotNull(message = "`customerEmail` cannot be null")
    @NotEmpty(message = "`customerEmail` cannot be empty")
    private String customerEmail;

    @NotNull(message = "`status` cannot be null")
    @NotEmpty(message = "`status` cannot be empty")
    private String status;
}
