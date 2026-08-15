package com.spms.parking.service;

import com.spms.parking.dto.ParkingSpaceRequest;
import com.spms.parking.exception.ResourceNotFoundException;
import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import com.spms.parking.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class ParkingSpaceService {

    private final ParkingSpaceRepository repository;

    public ParkingSpaceService(ParkingSpaceRepository repository) {
        this.repository = repository;
    }

    public ParkingSpace create(ParkingSpaceRequest request) {
        ParkingSpace space = new ParkingSpace(null, request.getLocation(), request.getCity(),
                request.getZone(), request.getOwnerId(), request.getPricePerHour());
        return repository.save(space);
    }

    public ParkingSpace getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking space not found with id: " + id));
    }

    public Collection<ParkingSpace> getAll() {
        return repository.findAll();
    }

    public Collection<ParkingSpace> search(String city, String zone, SpaceStatus status) {
        return repository.search(city, zone, status);
    }

    public Collection<ParkingSpace> getByOwner(Long ownerId) {
        return repository.findByOwnerId(ownerId);
    }

    public ParkingSpace update(Long id, ParkingSpaceRequest request) {
        ParkingSpace space = getById(id);
        if (request.getLocation() != null) space.setLocation(request.getLocation());
        if (request.getCity() != null) space.setCity(request.getCity());
        if (request.getZone() != null) space.setZone(request.getZone());
        if (request.getPricePerHour() > 0) space.setPricePerHour(request.getPricePerHour());
        space.setUpdatedAt(LocalDateTime.now());
        return repository.save(space);
    }

    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public ParkingSpace reserve(Long id) {
        ParkingSpace space = getById(id);
        if (space.getStatus() != SpaceStatus.AVAILABLE) {
            throw new IllegalStateException("Parking space is not available for reservation. Current status: " + space.getStatus());
        }
        space.setStatus(SpaceStatus.RESERVED);
        space.setUpdatedAt(LocalDateTime.now());
        return repository.save(space);
    }

    public ParkingSpace release(Long id) {
        ParkingSpace space = getById(id);
        space.setStatus(SpaceStatus.AVAILABLE);
        space.setUpdatedAt(LocalDateTime.now());
        return repository.save(space);
    }

    public ParkingSpace updateStatus(Long id, SpaceStatus status) {
        ParkingSpace space = getById(id);
        space.setStatus(status);
        space.setUpdatedAt(LocalDateTime.now());
        return repository.save(space);
    }
}
