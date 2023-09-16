package com.example.jwttask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String message;
}