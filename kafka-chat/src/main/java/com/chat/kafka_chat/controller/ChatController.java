package com.chat.kafka_chat.controller;

import com.chat.kafka_chat.model.ChatMessage;
import com.chat.kafka_chat.service.Chatservice;
import com.chat.kafka_chat.service.Chatservice;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@CrossOrigin(origins = "http://localhost:4200")
public class ChatController {

    private final Chatservice chatservice;

    @Autowired
    public ChatController(Chatservice chatservice) {
        this.chatservice = chatservice;
    }
    // Endpoint to send a message
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody ChatMessage message) throws JsonProcessingException {
        chatservice.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }

    // Endpoint to get all chat messages
    @GetMapping("/messages")
    public ResponseEntity<List<ChatMessage>> getMessages() {
        List<ChatMessage> messages = chatservice.getMessages();
        return ResponseEntity.ok(messages);
    }
}
