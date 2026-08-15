package com.spms.payment.repository;

import com.spms.payment.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PaymentRepository {

    private final ConcurrentHashMap<Long, Payment> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Payment save(Payment payment) {
        if (payment.getId() == null) {
            payment.setId(idGenerator.getAndIncrement());
        }
        store.put(payment.getId(), payment);
        return payment;
    }

    public Optional<Payment> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<Payment> findAll() {
        return store.values();
    }

    public Collection<Payment> findByUserId(Long userId) {
        return store.values().stream()
                .filter(p -> p.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Optional<Payment> findByReceiptId(String receiptId) {
        return store.values().stream()
                .filter(p -> receiptId.equals(p.getReceiptId()))
                .findFirst();
    }
}
