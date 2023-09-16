package com.example.jwttask.service.impl;


import com.example.jwttask.dto.MessageRequest;
import com.example.jwttask.dto.MessageResponse;
import com.example.jwttask.entity.Message;
import com.example.jwttask.entity.User;
import com.example.jwttask.repository.MessageRepository;
import com.example.jwttask.repository.UserRepository;
import com.example.jwttask.service.MessageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<MessageResponse> newMessage(MessageRequest message) {
        if (message.getMessage().startsWith("history ")) {
            String stringCount = message.getMessage().substring(8);
            return getMessageHistory(Integer.parseInt(stringCount));
        }
        User currentUser = userRepository.findUserByUsername(message.getUsername()).orElseThrow(() ->
                new EntityNotFoundException("User with username: " + message.getUsername() + " not found!"));

        Message newMessage = messageRepository.save(Message.builder()
                .text(message.getMessage())
                .user(currentUser)
                .build());

        return Optional.of(
                new MessageResponse(List.of(newMessage.getText())));
    }

    private Optional<MessageResponse> getMessageHistory(int count) {
        return Optional.of(
                new MessageResponse(
                        messageRepository.findLastMessages(count)
                                .stream()
                                .map(Message::getText)
                                .toList()));
    }
}

