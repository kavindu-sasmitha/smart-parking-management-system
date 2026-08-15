package com.spms.vehicle.model;

import java.time.LocalDateTime;

public class Vehicle {

    private Long id;
    private String plateNumber;
    private VehicleType type;
    private Long userId;
    private VehicleStatus status;
    private LocalDateTime lastEntryTime;
    private LocalDateTime lastExitTime;

    public Vehicle() {
    }

    public Vehicle(Long id, String plateNumber, VehicleType type, Long userId) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.type = type;
        this.userId = userId;
        this.status = VehicleStatus.OUTSIDE;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public VehicleStatus getStatus() { return status; }
    public void setStatus(VehicleStatus status) { this.status = status; }
    public LocalDateTime getLastEntryTime() { return lastEntryTime; }
    public void setLastEntryTime(LocalDateTime lastEntryTime) { this.lastEntryTime = lastEntryTime; }
    public LocalDateTime getLastExitTime() { return lastExitTime; }
    public void setLastExitTime(LocalDateTime lastExitTime) { this.lastExitTime = lastExitTime; }
}
