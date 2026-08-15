package com.spms.payment.service;

import com.spms.payment.dto.PaymentRequest;
import com.spms.payment.exception.PaymentValidationException;
import com.spms.payment.exception.ResourceNotFoundException;
import com.spms.payment.model.Payment;
import com.spms.payment.model.PaymentStatus;
import com.spms.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class PaymentService {

    private static final Pattern CARD_PATTERN = Pattern.compile("^\\d{16}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^\\d{3,4}$");
    private static final Pattern EXPIRY_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])/\\d{2}$");

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment processPayment(PaymentRequest request) {
        validateCardDetails(request);

        Payment payment = new Payment(null, request.getUserId(), request.getVehicleId(),
                request.getParkingSpaceId(), request.getAmount(), maskCard(request.getCardNumber()));

        // Simulate transaction processing - mock gateway always succeeds if validation passes
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setReceiptId("RCPT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        return repository.save(payment);
    }

    private void validateCardDetails(PaymentRequest request) {
        if (!CARD_PATTERN.matcher(request.getCardNumber()).matches()) {
            throw new PaymentValidationException("Card number must be exactly 16 digits");
        }
        if (!CVV_PATTERN.matcher(request.getCvv()).matches()) {
            throw new PaymentValidationException("CVV must be 3 or 4 digits");
        }
        if (!EXPIRY_PATTERN.matcher(request.getExpiry()).matches()) {
            throw new PaymentValidationException("Expiry must be in MM/YY format");
        }
        if (request.getAmount() <= 0) {
            throw new PaymentValidationException("Amount must be greater than zero");
        }
    }

    private String maskCard(String cardNumber) {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    public Payment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
    }

    public Collection<Payment> getAll() {
        return repository.findAll();
    }

    public Collection<Payment> getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

    public Payment getByReceiptId(String receiptId) {
        return repository.findByReceiptId(receiptId)
                .orElseThrow(() -> new ResourceNotFoundException("Receipt not found: " + receiptId));
    }
}
