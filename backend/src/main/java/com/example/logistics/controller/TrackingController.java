package com.example.logistics.controller;

import com.example.logistics.dto.TrackingImportRequest;
import com.example.logistics.entity.TrackingNumber;
import com.example.logistics.service.TrackingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/trackings")
public class TrackingController {
    private final TrackingService trackingService;

    public TrackingController(TrackingService trackingService) {
        this.trackingService = trackingService;
    }

    @PostMapping("/import")
    public ResponseEntity<List<TrackingNumber>> importTracking(@Valid @RequestBody TrackingImportRequest request) {
        return ResponseEntity.ok(trackingService.importNumbers(request));
    }

    @GetMapping
    public ResponseEntity<List<TrackingNumber>> list() {
        return ResponseEntity.ok(trackingService.list());
    }
}
