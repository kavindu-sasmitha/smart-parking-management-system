package com.spms.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PaymentRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotNull(message = "Vehicle id is required")
    private Long vehicleId;

    @NotNull(message = "Parking space id is required")
    private Long parkingSpaceId;

    @Positive(message = "Amount must be positive")
    private double amount;

    @NotBlank(message = "Card number is required")
    private String cardNumber;

    @NotBlank(message = "Card expiry (MM/YY) is required")
    private String expiry;

    @NotBlank(message = "CVV is required")
    private String cvv;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public Long getParkingSpaceId() { return parkingSpaceId; }
    public void setParkingSpaceId(Long parkingSpaceId) { this.parkingSpaceId = parkingSpaceId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}
