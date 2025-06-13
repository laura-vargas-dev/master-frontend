package com.unir.ms_books_payments.service;

import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.controller.model.PurchaseRequest;
import com.unir.ms_books_payments.data.model.Purchase;

import java.util.List;

public interface PurchaseService {

    List<Purchase> getPurchases();

    Purchase getPurchase(String id);

    Purchase createPurchase(PurchaseRequest request);

    Purchase updatePurchase(String bookId, String updateRequest);

    Purchase updatePurchase(String bookId, PurchaseDto updateRequest);

    Boolean deletePurchase(String id);
}
