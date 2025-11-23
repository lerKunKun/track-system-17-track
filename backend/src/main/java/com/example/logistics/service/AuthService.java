package com.example.logistics.service;

import com.example.logistics.dto.LoginRequest;
import com.example.logistics.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
