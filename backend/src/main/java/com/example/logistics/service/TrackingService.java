package com.example.logistics.service;

import com.example.logistics.dto.TrackingImportRequest;
import com.example.logistics.entity.TrackingNumber;

import java.util.List;

public interface TrackingService {
    List<TrackingNumber> importNumbers(TrackingImportRequest request);

    List<TrackingNumber> list();
}
