package com.relatos.msbookscatalogue.application.command;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateBookCommand {

    @NotBlank(message = "El título es obligatorio")
    private String title;

    @NotBlank(message = "El autor es obligatorio")
    private String author;

    @NotNull(message = "La fecha de publicación es obligatoria")
    private LocalDate publicationDate;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;

    @NotBlank(message = "El ISBN es obligatorio")
    private String isbn;

    @NotNull(message = "La valoración es obligatoria")
    @Min(1) @Max(5)
    private Integer rating;

    @NotNull(message = "Debe indicar visibilidad")
    private Boolean visible;

    @NotNull(message = "El stock es obligatorio")
    @Min(0)
    private Integer stock;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin("0.0")
    private Double price;
}