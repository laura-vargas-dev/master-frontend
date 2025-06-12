package com.unir.ms_books_payments.data;

import com.unir.ms_books_payments.data.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseJpaRepository extends JpaRepository<Purchase, Long> {
}
