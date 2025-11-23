package com.example.logistics.dto;

import jakarta.validation.constraints.NotBlank;

public class PlatformCredentialRequest {
    @NotBlank
    private String platform;
    @NotBlank
    private String apiKey;
    private String apiSecret;
    private String accessToken;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
