package com.spms.payment.dto;

import com.spms.payment.model.Payment;

public class ReceiptResponse {

    private String receiptId;
    private Long paymentId;
    private Long userId;
    private Long vehicleId;
    private Long parkingSpaceId;
    private double amount;
    private String status;
    private String issuedAt;

    public ReceiptResponse(Payment payment) {
        this.receiptId = payment.getReceiptId();
        this.paymentId = payment.getId();
        this.userId = payment.getUserId();
        this.vehicleId = payment.getVehicleId();
        this.parkingSpaceId = payment.getParkingSpaceId();
        this.amount = payment.getAmount();
        this.status = payment.getStatus().name();
        this.issuedAt = payment.getTimestamp().toString();
    }

    public String getReceiptId() { return receiptId; }
    public Long getPaymentId() { return paymentId; }
    public Long getUserId() { return userId; }
    public Long getVehicleId() { return vehicleId; }
    public Long getParkingSpaceId() { return parkingSpaceId; }
    public double getAmount() { return amount; }
    public String getStatus() { return status; }
    public String getIssuedAt() { return issuedAt; }
}
