package com.spms.vehicle.repository;

import com.spms.vehicle.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class VehicleRepository {

    private final ConcurrentHashMap<Long, Vehicle> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Vehicle save(Vehicle vehicle) {
        if (vehicle.getId() == null) {
            vehicle.setId(idGenerator.getAndIncrement());
        }
        store.put(vehicle.getId(), vehicle);
        return vehicle;
    }

    public Optional<Vehicle> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<Vehicle> findByPlateNumber(String plateNumber) {
        return store.values().stream()
                .filter(v -> v.getPlateNumber().equalsIgnoreCase(plateNumber))
                .findFirst();
    }

    public Collection<Vehicle> findAll() {
        return store.values();
    }

    public Collection<Vehicle> findByUserId(Long userId) {
        return store.values().stream()
                .filter(v -> v.getUserId().equals(userId))
                .toList();
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existsByPlateNumber(String plateNumber) {
        return findByPlateNumber(plateNumber).isPresent();
    }
}
