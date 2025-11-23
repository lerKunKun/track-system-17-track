package com.example.logistics.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class TrackingImportRequest {
    @NotEmpty
    private List<String> trackingNumbers;
    private String carrierCode;

    public List<String> getTrackingNumbers() {
        return trackingNumbers;
    }

    public void setTrackingNumbers(List<String> trackingNumbers) {
        this.trackingNumbers = trackingNumbers;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }
}
