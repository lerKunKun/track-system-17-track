package com.example.logistics.service.impl;

import com.example.logistics.dto.PlatformCredentialRequest;
import com.example.logistics.dto.ShopRequest;
import com.example.logistics.entity.Shop;
import com.example.logistics.service.ShopService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ShopServiceImpl implements ShopService {
    private final Map<Long, Shop> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Shop create(ShopRequest request) {
        Shop shop = new Shop();
        shop.setId(idGenerator.getAndIncrement());
        shop.setShopName(request.getShopName());
        shop.setPlatform(request.getPlatform());
        shop.setStoreUrl(request.getStoreUrl());
        shop.setApiKey(request.getApiKey());
        shop.setApiSecret(request.getApiSecret());
        shop.setAccessToken(request.getAccessToken());
        shop.setTimezone(request.getTimezone());
        LocalDateTime now = LocalDateTime.now();
        shop.setCreatedAt(now);
        shop.setUpdatedAt(now);
        storage.put(shop.getId(), shop);
        return shop;
    }

    @Override
    public List<Shop> list() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Shop updateCredentials(Long shopId, PlatformCredentialRequest request) {
        Shop shop = Optional.ofNullable(storage.get(shopId))
                .orElseThrow(() -> new IllegalArgumentException("Shop not found"));
        shop.setApiKey(request.getApiKey());
        shop.setApiSecret(request.getApiSecret());
        shop.setAccessToken(request.getAccessToken());
        shop.setUpdatedAt(LocalDateTime.now());
        return shop;
    }
}
