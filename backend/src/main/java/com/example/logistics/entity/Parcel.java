package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Parcel {
    private Long id;
    private Long orderId;
    private String parcelNo;
    private String carrierCode;
    private String carrierName;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
