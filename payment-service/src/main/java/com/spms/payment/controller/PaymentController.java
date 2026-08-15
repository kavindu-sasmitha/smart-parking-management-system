package com.spms.payment.controller;

import com.spms.payment.dto.PaymentRequest;
import com.spms.payment.dto.ReceiptResponse;
import com.spms.payment.model.Payment;
import com.spms.payment.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Payment> processPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.processPayment(request));
    }

    @GetMapping
    public ResponseEntity<Collection<Payment>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Collection<Payment>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @GetMapping("/receipt/{receiptId}")
    public ResponseEntity<ReceiptResponse> getReceipt(@PathVariable String receiptId) {
        return ResponseEntity.ok(new ReceiptResponse(service.getByReceiptId(receiptId)));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Payment Service is UP");
    }
}
