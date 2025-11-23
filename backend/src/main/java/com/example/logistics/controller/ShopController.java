package com.example.logistics.controller;

import com.example.logistics.dto.PlatformCredentialRequest;
import com.example.logistics.dto.ShopRequest;
import com.example.logistics.entity.Shop;
import com.example.logistics.service.ShopService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopController {
    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<Shop> create(@Valid @RequestBody ShopRequest request) {
        return ResponseEntity.ok(shopService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<Shop>> list() {
        return ResponseEntity.ok(shopService.list());
    }

    @PutMapping("/{id}/credentials")
    public ResponseEntity<Shop> updateCredentials(@PathVariable("id") Long shopId,
                                                  @Valid @RequestBody PlatformCredentialRequest request) {
        return ResponseEntity.ok(shopService.updateCredentials(shopId, request));
    }
}
