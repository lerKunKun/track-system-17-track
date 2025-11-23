package com.example.logistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Carrier {
    private Long id;
    private String carrierCode;
    private String carrierName;
    private String carrierCountry;
    private String servicePhone;
    private String website;
    private LocalDateTime createdAt;
}
