package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Shop {
    private Long id;
    private String shopName;
    private String platform;
    private String storeUrl;
    private String apiKey;
    private String apiSecret;
    private String accessToken;
    private String timezone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
