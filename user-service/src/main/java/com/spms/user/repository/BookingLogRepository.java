package com.spms.user.repository;

import com.spms.user.model.BookingLog;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class BookingLogRepository {

    private final ConcurrentHashMap<Long, BookingLog> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public BookingLog save(BookingLog log) {
        if (log.getId() == null) {
            log.setId(idGenerator.getAndIncrement());
        }
        store.put(log.getId(), log);
        return log;
    }

    public List<BookingLog> findByUserId(Long userId) {
        return store.values().stream()
                .filter(l -> l.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Collection<BookingLog> findAll() {
        return store.values();
    }
}
