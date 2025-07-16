package com.chat.kafka_chat.service;
import com.chat.kafka_chat.model.ChatMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
@Service
public class Chatservice {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final List<ChatMessage> messages = new CopyOnWriteArrayList<>();

    public void sendMessage(ChatMessage msg) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        kafkaTemplate.send("chat-topic", mapper.writeValueAsString(msg));
    }

    @KafkaListener(topics = "chat-topic", groupId = "chat-group")
    public void listen(String messageJson) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ChatMessage msg = mapper.readValue(messageJson, ChatMessage.class);
        messages.add(msg);
    }

    //returns all the messages
    public  List<ChatMessage> getMessages() {
        return messages;}

}
