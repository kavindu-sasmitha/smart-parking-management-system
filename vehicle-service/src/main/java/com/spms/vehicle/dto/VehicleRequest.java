package com.spms.vehicle.dto;

import com.spms.vehicle.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleRequest {

    @NotBlank(message = "Plate number is required")
    private String plateNumber;

    @NotNull(message = "Vehicle type is required")
    private VehicleType type;

    @NotNull(message = "User id is required")
    private Long userId;

    public String getPlateNumber() { return plateNumber; }
    public void setPlateNumber(String plateNumber) { this.plateNumber = plateNumber; }
    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
