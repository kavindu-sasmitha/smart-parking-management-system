package com.spms.parking.model;

import java.time.LocalDateTime;

public class ParkingSpace {

    private Long id;
    private String location;
    private String city;
    private String zone;
    private Long ownerId;
    private double pricePerHour;
    private SpaceStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ParkingSpace() {
    }

    public ParkingSpace(Long id, String location, String city, String zone, Long ownerId, double pricePerHour) {
        this.id = id;
        this.location = location;
        this.city = city;
        this.zone = zone;
        this.ownerId = ownerId;
        this.pricePerHour = pricePerHour;
        this.status = SpaceStatus.AVAILABLE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
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
    public SpaceStatus getStatus() { return status; }
    public void setStatus(SpaceStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
