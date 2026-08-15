package com.spms.vehicle.model;

import java.time.LocalDateTime;

public class EntryExitLog {

    private Long id;
    private Long vehicleId;
    private String action;
    private LocalDateTime timestamp;

    public EntryExitLog() {
    }

    public EntryExitLog(Long id, Long vehicleId, String action) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.action = action;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
