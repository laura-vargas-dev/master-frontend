package com.unir.ms_books_payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.controller.model.PurchaseRequest;
import com.unir.ms_books_payments.data.PurchaseRepository;
import com.unir.ms_books_payments.data.model.Purchase;
import com.unir.ms_books_payments.facade.BooksFacade;
import com.unir.ms_books_payments.facade.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private BooksFacade booksFacade;

    @Autowired
    private PurchaseRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Purchase> getPurchases() {
        List<Purchase> purchases = repository.getPurchase();
        return purchases.isEmpty() ? null : purchases;
    }

    @Override
    public Purchase getPurchase(String id) {
        return repository.getById(Long.valueOf(id));
    }

    @Override
    public Purchase createPurchase(PurchaseRequest request) {
        List<Book> books = request.getBooks().stream().map(booksFacade::getBook).filter(Objects::nonNull).toList();
        if(books.size() != request.getBooks().size() || books.stream().anyMatch(book -> !book.getVisible())) {
            return null;
        } else {
            Purchase purchase = Purchase.builder()
                    .purchaseDate(request.getPurchaseDate()).totalAmount(request.getTotalAmount())
                    .books(books.stream().map(Book::getId).collect(Collectors.toList()))
                    .customerName(request.getCustomerName()).customerEmail(request.getCustomerEmail())
                    .status(request.getStatus()).build();
            return repository.save(purchase);
        }
    }

    @Override
    public Purchase updatePurchase(String purchaseId, PurchaseDto updateRequest) {
        Purchase purchase = repository.getById(Long.valueOf(purchaseId));
        if (purchase != null) {
            purchase.update(updateRequest);
            repository.save(purchase);
            return purchase;
        } else {
            return null;
        }
    }

    @Override
    public Purchase updatePurchase(String purchaseId, String updateRequest) {
        Purchase purchase = repository.getById(Long.valueOf(purchaseId));
        if (purchase != null) {
            try {
                JsonMergePatch jsonMergePatch = JsonMergePatch.fromJson(objectMapper.readTree(updateRequest));
                JsonNode target = jsonMergePatch.apply(objectMapper.readTree(objectMapper.writeValueAsString(purchase)));
                Purchase patched = objectMapper.treeToValue(target, Purchase.class);
                repository.save(patched);
                return patched;
            } catch (JsonProcessingException | JsonPatchException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public Boolean deletePurchase(String purchaseId) {
        Purchase purchase = repository.getById(Long.valueOf(purchaseId));
        if (purchase != null) {
            repository.delete(purchase);
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
