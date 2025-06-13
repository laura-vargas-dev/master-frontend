package com.unir.ms_books_payments.data.model;

import com.unir.ms_books_payments.controller.model.PurchaseDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @ElementCollection
    @CollectionTable(
            name = "purchase_books",
            joinColumns = @JoinColumn(name = "purchase_id")
    )
    @Column(name = "book", nullable = false)
    private List<Long> books;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "customer_email", nullable = false)
    private String customerEmail;

    @Column(nullable = false)
    private String status;

    public void update(PurchaseDto purchaseDto) {
        this.purchaseDate = purchaseDto.getPurchaseDate();
        this.totalAmount = purchaseDto.getTotalAmount();
        this.books = purchaseDto.getBooks();
        this.customerName = purchaseDto.getCustomerName();
        this.customerEmail = purchaseDto.getCustomerEmail();
        this.status = purchaseDto.getStatus();
    }
}
