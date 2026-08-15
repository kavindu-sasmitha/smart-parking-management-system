package com.spms.vehicle.service;

import com.spms.vehicle.dto.VehicleRequest;
import com.spms.vehicle.exception.ResourceNotFoundException;
import com.spms.vehicle.model.EntryExitLog;
import com.spms.vehicle.model.Vehicle;
import com.spms.vehicle.model.VehicleStatus;
import com.spms.vehicle.repository.EntryExitLogRepository;
import com.spms.vehicle.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final EntryExitLogRepository entryExitLogRepository;

    public VehicleService(VehicleRepository vehicleRepository, EntryExitLogRepository entryExitLogRepository) {
        this.vehicleRepository = vehicleRepository;
        this.entryExitLogRepository = entryExitLogRepository;
    }

    public Vehicle register(VehicleRequest request) {
        if (vehicleRepository.existsByPlateNumber(request.getPlateNumber())) {
            throw new IllegalArgumentException("Vehicle already registered with plate: " + request.getPlateNumber());
        }
        Vehicle vehicle = new Vehicle(null, request.getPlateNumber(), request.getType(), request.getUserId());
        return vehicleRepository.save(vehicle);
    }

    public Vehicle getById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    public Collection<Vehicle> getAll() {
        return vehicleRepository.findAll();
    }

    public Collection<Vehicle> getByUserId(Long userId) {
        return vehicleRepository.findByUserId(userId);
    }

    public Vehicle update(Long id, VehicleRequest request) {
        Vehicle vehicle = getById(id);
        if (request.getPlateNumber() != null) vehicle.setPlateNumber(request.getPlateNumber());
        if (request.getType() != null) vehicle.setType(request.getType());
        if (request.getUserId() != null) vehicle.setUserId(request.getUserId());
        return vehicleRepository.save(vehicle);
    }

    public void delete(Long id) {
        getById(id);
        vehicleRepository.deleteById(id);
    }

    public Vehicle recordEntry(Long id) {
        Vehicle vehicle = getById(id);
        if (vehicle.getStatus() == VehicleStatus.INSIDE) {
            throw new IllegalStateException("Vehicle is already inside the parking premises");
        }
        vehicle.setStatus(VehicleStatus.INSIDE);
        vehicle.setLastEntryTime(LocalDateTime.now());
        vehicleRepository.save(vehicle);
        entryExitLogRepository.save(new EntryExitLog(null, id, "ENTRY"));
        return vehicle;
    }

    public Vehicle recordExit(Long id) {
        Vehicle vehicle = getById(id);
        if (vehicle.getStatus() == VehicleStatus.OUTSIDE) {
            throw new IllegalStateException("Vehicle is already outside the parking premises");
        }
        vehicle.setStatus(VehicleStatus.OUTSIDE);
        vehicle.setLastExitTime(LocalDateTime.now());
        vehicleRepository.save(vehicle);
        entryExitLogRepository.save(new EntryExitLog(null, id, "EXIT"));
        return vehicle;
    }

    public List<EntryExitLog> getLogs(Long vehicleId) {
        getById(vehicleId);
        return entryExitLogRepository.findByVehicleId(vehicleId);
    }
}
