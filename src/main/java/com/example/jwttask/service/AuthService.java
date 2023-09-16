package com.example.jwttask.service;

import com.example.jwttask.dto.JwtResponse;
import com.example.jwttask.dto.LoginRequest;


import java.util.Optional;



public interface AuthService {
    Optional<JwtResponse> login(LoginRequest loginRequest);

    boolean isValid(String authHeader);
}