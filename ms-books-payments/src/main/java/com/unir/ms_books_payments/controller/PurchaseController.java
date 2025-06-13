package com.unir.ms_books_payments.controller;

import com.unir.ms_books_payments.controller.model.PurchaseDto;
import com.unir.ms_books_payments.controller.model.PurchaseRequest;
import com.unir.ms_books_payments.data.model.Purchase;
import com.unir.ms_books_payments.service.PurchaseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/purchases")
public class PurchaseController {

    private final PurchaseService service;

    // GET /api/purchases
    @GetMapping
    public ResponseEntity<List<Purchase>> getPurchases() {
        List<Purchase> purchases = service.getPurchases();
        return ResponseEntity.ok(Objects.requireNonNullElse(purchases, Collections.emptyList()));
    }

    // GET /api/purchases/{purchaseId}
    @GetMapping("/{purchaseId}")
    public ResponseEntity<Purchase> getOrder(@PathVariable String purchaseId) {
        Purchase order = service.getPurchase(purchaseId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /api/purchases/
    @PostMapping
    public ResponseEntity<Purchase> createOrder(@RequestBody @Valid PurchaseRequest request) {
        Purchase created = service.createPurchase(request);
        if (created != null) {
            return ResponseEntity.ok(created);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    // PUT /api/purchases/
    @PutMapping("/{purchaseId}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable String purchaseId, @RequestBody PurchaseDto body) {
        Purchase updated = service.updatePurchase(purchaseId, body);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // PATCH /api/purchases/{purchaseId}
    @PatchMapping("/{purchaseId}")
    public ResponseEntity<Purchase> patchBook(@PathVariable String purchaseId, @RequestBody String patchBody) {
        Purchase patched = service.updatePurchase(purchaseId, patchBody);
        if (patched != null) {
            return ResponseEntity.ok(patched);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    // DELETE /api/purchases/{purchaseId}
    @DeleteMapping("/{purchaseId}")
    public ResponseEntity<Void> deletePurchase(@PathVariable String purchaseId) {
        Boolean removed = service.deletePurchase(purchaseId);
        if (Boolean.TRUE.equals(removed)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
