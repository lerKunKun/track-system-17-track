package com.example.logistics.service.impl;

import com.example.logistics.dto.TrackingImportRequest;
import com.example.logistics.entity.TrackingNumber;
import com.example.logistics.service.TrackingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TrackingServiceImpl implements TrackingService {
    private final Map<Long, TrackingNumber> storage = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public List<TrackingNumber> importNumbers(TrackingImportRequest request) {
        List<TrackingNumber> imported = new ArrayList<>();
        for (String number : request.getTrackingNumbers()) {
            TrackingNumber tn = new TrackingNumber();
            tn.setId(idGenerator.getAndIncrement());
            tn.setTrackingNumber(number);
            tn.setCarrierCode(request.getCarrierCode());
            tn.setSource("manual");
            LocalDateTime now = LocalDateTime.now();
            tn.setCreatedAt(now);
            tn.setUpdatedAt(now);
            storage.put(tn.getId(), tn);
            imported.add(tn);
        }
        return imported;
    }

    @Override
    public List<TrackingNumber> list() {
        return new ArrayList<>(storage.values());
    }
}
