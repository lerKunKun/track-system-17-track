package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingEvent {
    private Long id;
    private Long trackingId;
    private String status;
    private String location;
    private String stage;
    private String description;
    private LocalDateTime eventTime;
    private String raw;
    private LocalDateTime createdAt;
}
