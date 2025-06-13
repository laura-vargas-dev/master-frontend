package com.unir.ms_books_catalogue.data.model;

import com.unir.ms_books_catalogue.controller.model.BookDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                             // PK autonumérico

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 255)
    private String title;                        // Título del libro

    @NotBlank(message = "El autor es obligatorio")
    @Column(nullable = false, length = 255)
    private String author;                       // Autor del libro

    @NotNull(message = "La fecha de publicación es obligatoria")
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;           // Fecha de publicación

    @NotBlank(message = "La categoría es obligatoria")
    @Column(nullable = false, length = 100)
    private String category;                     // Categoría (gènero)

    @NotBlank(message = "El ISBN es obligatorio")
    @Column(nullable = false, unique = true, length = 20)
    private String isbn;                         // ISBN único

    @NotNull(message = "La valoración es obligatoria")
    @Min(value = 1, message = "La valoración mínima es 1")
    @Max(value = 5, message = "La valoración máxima es 5")
    @Column(nullable = false)
    private Double rating;                      // Valoración (1 a 5)

    @NotNull(message = "Se debe indicar si es visible")
    @Column(nullable = false)
    @Builder.Default
    private Boolean visible = true;              // Visible en catálogo

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(nullable = false)
    @Builder.Default
    private Integer stock = 0;                   // Cantidad en inventario

    @NotNull(message = "El precio es obligatorio")
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Column(nullable = false)
    @Builder.Default
    private Double price = 0.0;

    public void update(BookDto bookDto) {
        this.title = bookDto.getTitle();
        this.author = bookDto.getAuthor();
        this.publicationDate = bookDto.getPublicationDate();
        this.category = bookDto.getCategory();
        this.isbn = bookDto.getIsbn();
        this.rating = bookDto.getRating();
        this.visible = bookDto.getVisible();
        this.stock = bookDto.getStock();
        this.price = bookDto.getPrice();
    }
}
