package com.example.logistics.service.impl;

import com.example.logistics.config.SecurityProperties;
import com.example.logistics.dto.LoginRequest;
import com.example.logistics.dto.LoginResponse;
import com.example.logistics.service.AuthService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {
    private final SecurityProperties properties;

    public AuthServiceImpl(SecurityProperties properties) {
        this.properties = properties;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        if (!properties.getAdmin().getUsername().equals(request.getUsername()) ||
                !properties.getAdmin().getPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        long expiration = properties.getJwt().getExpiration();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);
        SecretKey key = Keys.hmacShaKeyFor(properties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .setSubject(request.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return new LoginResponse(token, expiration / 1000);
    }
}
