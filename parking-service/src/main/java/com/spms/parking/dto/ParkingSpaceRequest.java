package com.spms.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ParkingSpaceRequest {

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Zone is required")
    private String zone;

    @NotNull(message = "Owner id is required")
    private Long ownerId;

    @Positive(message = "Price per hour must be positive")
    private double pricePerHour;

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getZone() { return zone; }
    public void setZone(String zone) { this.zone = zone; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }
    public double getPricePerHour() { return pricePerHour; }
    public void setPricePerHour(double pricePerHour) { this.pricePerHour = pricePerHour; }
}
