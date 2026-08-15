package com.spms.parking.controller;

import com.spms.parking.dto.ParkingSpaceRequest;
import com.spms.parking.dto.StatusUpdateRequest;
import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import com.spms.parking.service.ParkingSpaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/parking")
public class ParkingSpaceController {

    private final ParkingSpaceService service;

    public ParkingSpaceController(ParkingSpaceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ParkingSpace> create(@Valid @RequestBody ParkingSpaceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @GetMapping
    public ResponseEntity<Collection<ParkingSpace>> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zone,
            @RequestParam(required = false) SpaceStatus status) {
        return ResponseEntity.ok(service.search(city, zone, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpace> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<Collection<ParkingSpace>> getByOwner(@PathVariable Long ownerId) {
        return ResponseEntity.ok(service.getByOwner(ownerId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpace> update(@PathVariable Long id, @RequestBody ParkingSpaceRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/reserve")
    public ResponseEntity<ParkingSpace> reserve(@PathVariable Long id) {
        return ResponseEntity.ok(service.reserve(id));
    }

    @PutMapping("/{id}/release")
    public ResponseEntity<ParkingSpace> release(@PathVariable Long id) {
        return ResponseEntity.ok(service.release(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ParkingSpace> updateStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateRequest request) {
        return ResponseEntity.ok(service.updateStatus(id, request.getStatus()));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Parking Space Service is UP");
    }
}
