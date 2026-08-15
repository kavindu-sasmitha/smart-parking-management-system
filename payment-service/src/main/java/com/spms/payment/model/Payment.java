package com.spms.payment.model;

import java.time.LocalDateTime;

public class Payment {

    private Long id;
    private Long userId;
    private Long vehicleId;
    private Long parkingSpaceId;
    private double amount;
    private String maskedCardNumber;
    private PaymentStatus status;
    private String receiptId;
    private LocalDateTime timestamp;

    public Payment() {
    }

    public Payment(Long id, Long userId, Long vehicleId, Long parkingSpaceId, double amount, String maskedCardNumber) {
        this.id = id;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.parkingSpaceId = parkingSpaceId;
        this.amount = amount;
        this.maskedCardNumber = maskedCardNumber;
        this.status = PaymentStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public Long getParkingSpaceId() { return parkingSpaceId; }
    public void setParkingSpaceId(Long parkingSpaceId) { this.parkingSpaceId = parkingSpaceId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getMaskedCardNumber() { return maskedCardNumber; }
    public void setMaskedCardNumber(String maskedCardNumber) { this.maskedCardNumber = maskedCardNumber; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
    public String getReceiptId() { return receiptId; }
    public void setReceiptId(String receiptId) { this.receiptId = receiptId; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
