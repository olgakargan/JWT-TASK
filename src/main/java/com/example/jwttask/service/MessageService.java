package com.example.jwttask.service;


import com.example.jwttask.dto.MessageRequest;
import com.example.jwttask.dto.MessageResponse;

import java.util.Optional;

public interface MessageService {
    Optional<MessageResponse> newMessage(MessageRequest message);
}