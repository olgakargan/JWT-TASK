package com.example.jwttask.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;

import com.example.jwttask.dto.JwtResponse;
import com.example.jwttask.dto.LoginRequest;
import com.example.jwttask.entity.User;
import com.example.jwttask.repository.UserRepository;
import com.example.jwttask.service.AuthService;
import com.example.jwttask.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public Optional<JwtResponse> login(LoginRequest loginRequest) {
        Optional<User> currentUser = userRepository.findUserByUsername(loginRequest.getName());

        if (currentUser.isEmpty()) {
            log.error("User not found!");
            return Optional.empty();
        }

        if (!validBCryptPassword(loginRequest.getPassword(), currentUser.get().getPassword())) {
            log.error("Wrong password!");
            return Optional.empty();
        }

        String token = jwtService.generateJwtToken(loginRequest.getName());

        return Optional.of(JwtResponse.builder().token(token).build());
    }

    @Override
    public boolean isValid(String authHeader) {
        String jwt = authHeader.substring(7);
        String username = jwtService.getUserNameFromJwtToken(jwt);

        return !username.isEmpty() || userRepository.existsByUsername(username);


    }

    private boolean validBCryptPassword(String inComingPassword, String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(inComingPassword.toCharArray(), password);
        return result.verified;
    }

}