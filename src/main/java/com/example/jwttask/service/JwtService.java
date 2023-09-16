package com.example.jwttask.service;

public interface JwtService {
    String generateJwtToken(String username);

    String getUserNameFromJwtToken(String token);
}
