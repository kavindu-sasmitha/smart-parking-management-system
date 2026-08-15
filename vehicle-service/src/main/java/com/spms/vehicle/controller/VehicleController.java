package com.spms.vehicle.controller;

import com.spms.vehicle.dto.VehicleRequest;
import com.spms.vehicle.model.EntryExitLog;
import com.spms.vehicle.model.Vehicle;
import com.spms.vehicle.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> register(@Valid @RequestBody VehicleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.register(request));
    }

    @GetMapping
    public ResponseEntity<Collection<Vehicle>> getAll(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return ResponseEntity.ok(vehicleService.getByUserId(userId));
        }
        return ResponseEntity.ok(vehicleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getById(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> update(@PathVariable Long id, @RequestBody VehicleRequest request) {
        return ResponseEntity.ok(vehicleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/entry")
    public ResponseEntity<Vehicle> recordEntry(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.recordEntry(id));
    }

    @PostMapping("/{id}/exit")
    public ResponseEntity<Vehicle> recordExit(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.recordExit(id));
    }

    @GetMapping("/{id}/logs")
    public ResponseEntity<List<EntryExitLog>> getLogs(@PathVariable Long id) {
        return ResponseEntity.ok(vehicleService.getLogs(id));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Vehicle Service is UP");
    }
}
