package com.imaginarynil.broadcast_server.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    private String username;
    private String content;
    private MessageType messageType;
    private String createdAt;
}
