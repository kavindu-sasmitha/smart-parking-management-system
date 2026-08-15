package com.spms.vehicle.repository;

import com.spms.vehicle.model.EntryExitLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class EntryExitLogRepository {

    private final ConcurrentHashMap<Long, EntryExitLog> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public EntryExitLog save(EntryExitLog log) {
        if (log.getId() == null) {
            log.setId(idGenerator.getAndIncrement());
        }
        store.put(log.getId(), log);
        return log;
    }

    public List<EntryExitLog> findByVehicleId(Long vehicleId) {
        return store.values().stream()
                .filter(l -> l.getVehicleId().equals(vehicleId))
                .collect(Collectors.toList());
    }
}
