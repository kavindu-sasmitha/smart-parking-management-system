package com.spms.user.model;

import java.time.LocalDateTime;

public class BookingLog {

    private Long id;
    private Long userId;
    private String description;
    private LocalDateTime timestamp;

    public BookingLog() {
    }

    public BookingLog(Long id, Long userId, String description) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
