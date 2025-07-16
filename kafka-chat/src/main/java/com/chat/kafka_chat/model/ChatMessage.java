package com.chat.kafka_chat.model;

public class ChatMessage {

        private String content;
        private String sessionId;

        public ChatMessage() {
        }
        public ChatMessage(String sender, String content) {

            this.content = content;
        }
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }
    }

