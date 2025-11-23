package com.example.logistics.service;

import com.example.logistics.dto.PlatformCredentialRequest;
import com.example.logistics.dto.ShopRequest;
import com.example.logistics.entity.Shop;

import java.util.List;

public interface ShopService {
    Shop create(ShopRequest request);

    List<Shop> list();

    Shop updateCredentials(Long shopId, PlatformCredentialRequest request);
}
