package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SyncJob {
    private Long id;
    private Long trackingId;
    private String status;
    private String errorMessage;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
}
