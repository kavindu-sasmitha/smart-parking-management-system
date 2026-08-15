package com.spms.user.repository;

import com.spms.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository {

    private final ConcurrentHashMap<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        store.put(user.getId(), user);
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return store.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public Collection<User> findAll() {
        return store.values();
    }

    public void deleteById(Long id) {
        store.remove(id);
    }

    public boolean existsByEmail(String email) {
        return findByEmail(email).isPresent();
    }
}
