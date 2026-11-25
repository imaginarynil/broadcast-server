package com.imaginarynil.broadcast_server.chat;

import com.imaginarynil.broadcast_server.user.User;
import com.imaginarynil.broadcast_server.user.UserNotFoundException;
import com.imaginarynil.broadcast_server.user.UserRepository;
import com.imaginarynil.broadcast_server.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {
    UserRepository userRepository;
    ChatRepository chatRepository;

    public void saveChat(ChatMessage chatMessage) {
        String username = chatMessage.getUsername();
        // connect or disconnect message from system, user == null
        Chat chat = Chat.builder().message(chatMessage.getContent()).build();
        if(username.isEmpty()) {
            chatRepository.save(chat);
            return;
        }
        Optional<User> savedUser = userRepository.findByUsername(username);
        if(savedUser.isEmpty()) {
            throw new UserNotFoundException(UserNotFoundException.getErrorMessageWithUsername(username));
        }
        chat.setUser(savedUser.get());
        chatRepository.save(chat);
    }
}
