package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingNumber {
    private Long id;
    private Long parcelId;
    private String trackingNumber;
    private String carrierCode;
    private String source;
    private String trackStatus;
    private LocalDateTime lastSyncAt;
    private LocalDateTime nextSyncAt;
    private String rawStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
