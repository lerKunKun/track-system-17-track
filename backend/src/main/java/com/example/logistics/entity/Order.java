package com.example.logistics.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long shopId;
    private String orderId;
    private String externalOrderId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private BigDecimal totalPrice;
    private String currency;
    private String fulfillmentStatus;
    private String financialStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
