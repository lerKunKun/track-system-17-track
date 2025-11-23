package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingWebhookLog {
    private Long id;
    private Long trackingId;
    private String payload;
    private LocalDateTime receivedAt;
}
