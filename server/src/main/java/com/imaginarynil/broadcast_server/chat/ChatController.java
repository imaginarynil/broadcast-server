package com.imaginarynil.broadcast_server.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@CrossOrigin
public class ChatController {
    public String getCurrentTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        return localDateTime.format(dateTimeFormatter);
    }

    @MessageMapping("/chat.connectUser")
    @SendTo("/topic/public")
    public ChatMessage connectUser(@Payload ChatMessage chatMessage) throws Exception {
        String content = chatMessage.getUsername() + " joined the chat";
        return new ChatMessage(chatMessage.getUsername(), content, MessageType.CONNECTED, getCurrentTime());
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) throws Exception {
        return new ChatMessage(chatMessage.getUsername(), chatMessage.getContent(), MessageType.CHATTING, getCurrentTime());
    }

    @MessageMapping("/chat.disconnectUser")
    @SendTo("/topic/public")
    public ChatMessage disconnectUser(@Payload ChatMessage chatMessage) throws Exception {
        String content = chatMessage.getUsername() + " left the chat";
        return new ChatMessage(chatMessage.getUsername(), content, MessageType.DISCONNECTED, getCurrentTime());
    }
}
