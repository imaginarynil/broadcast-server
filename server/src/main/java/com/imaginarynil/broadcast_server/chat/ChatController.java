package com.imaginarynil.broadcast_server.chat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
public class ChatController {
    @MessageMapping("/chat.connectUser")
    @SendTo("/topic/public")
    public ChatMessage connectUser(@Payload ChatMessage chatMessage) throws Exception {

        return new ChatMessage(chatMessage.getUsername(), chatMessage.getUsername() + " joined the chat", MessageType.CONNECTED);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) throws Exception {
        return new ChatMessage(chatMessage.getUsername(), chatMessage.getContent(), MessageType.CHATTING);
    }

    @MessageMapping("/chat.disconnectUser")
    @SendTo("/topic/public")
    public ChatMessage disconnectUser(@Payload ChatMessage chatMessage) throws Exception {
        return new ChatMessage(chatMessage.getUsername(), chatMessage.getUsername() + " left the chat", MessageType.DISCONNECTED);
    }
}
