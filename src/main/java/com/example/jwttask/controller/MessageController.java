package com.example.jwttask.controller;


import com.example.jwttask.dto.MessageRequest;
import com.example.jwttask.service.AuthService;
import com.example.jwttask.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final AuthService authService;

    @PostMapping("/message")
    public ResponseEntity<?> newMessage(@Valid @RequestBody MessageRequest message,
                                        @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        if(authHeader == null || !authHeader.startsWith("Bearer_") || !authService.isValid(authHeader)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return messageService.newMessage(message)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }
}