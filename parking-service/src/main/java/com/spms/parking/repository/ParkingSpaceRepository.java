package com.spms.parking.repository;

import com.spms.parking.model.ParkingSpace;
import com.spms.parking.model.SpaceStatus;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class ParkingSpaceRepository {

    private final ConcurrentHashMap<Long, ParkingSpace> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ParkingSpace save(ParkingSpace space) {
        if (space.getId() == null) {
            space.setId(idGenerator.getAndIncrement());
        }
        store.put(space.getId(), space);
        return space;
    }

    public Optional<ParkingSpace> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Collection<ParkingSpace> findAll() {
        return store.values();
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public Collection<ParkingSpace> search(String city, String zone, SpaceStatus status) {
        return store.values().stream()
                .filter(s -> city == null || s.getCity().equalsIgnoreCase(city))
                .filter(s -> zone == null || s.getZone().equalsIgnoreCase(zone))
                .filter(s -> status == null || s.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Collection<ParkingSpace> findByOwnerId(Long ownerId) {
        return store.values().stream()
                .filter(s -> s.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());
    }
}
