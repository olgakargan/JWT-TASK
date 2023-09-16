package com.example.jwttask.controller;



import com.example.jwttask.dto.JwtResponse;
import com.example.jwttask.dto.LoginRequest;
import com.example.jwttask.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Optional<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

}