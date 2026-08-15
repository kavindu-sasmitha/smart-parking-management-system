package com.spms.user.service;

import com.spms.user.dto.LoginRequest;
import com.spms.user.dto.RegisterRequest;
import com.spms.user.dto.UpdateUserRequest;
import com.spms.user.exception.ResourceNotFoundException;
import com.spms.user.model.BookingLog;
import com.spms.user.model.User;
import com.spms.user.repository.BookingLogRepository;
import com.spms.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookingLogRepository bookingLogRepository;

    public UserService(UserRepository userRepository, BookingLogRepository bookingLogRepository) {
        this.userRepository = userRepository;
        this.bookingLogRepository = bookingLogRepository;
    }

    public User register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }
        User user = new User(null, request.getName(), request.getEmail(),
                request.getPassword(), request.getPhone(), request.getRole());
        User saved = userRepository.save(user);
        bookingLogRepository.save(new BookingLog(null, saved.getId(), "Account registered"));
        return saved;
    }

    public User login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));
        if (!user.getPassword().equals(request.getPassword())) {
            throw new ResourceNotFoundException("Invalid email or password");
        }
        return user;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public Collection<User> getAll() {
        return userRepository.findAll();
    }

    public User update(Long id, UpdateUserRequest request) {
        User user = getById(id);
        if (request.getName() != null && !request.getName().isBlank()) {
            user.setName(request.getName());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            user.setPhone(request.getPhone());
        }
        return userRepository.save(user);
    }

    public void delete(Long id) {
        getById(id);
        userRepository.deleteById(id);
    }

    public List<BookingLog> getBookingHistory(Long userId) {
        getById(userId);
        return bookingLogRepository.findByUserId(userId);
    }
}
