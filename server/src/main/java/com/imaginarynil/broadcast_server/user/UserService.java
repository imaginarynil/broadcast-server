package com.imaginarynil.broadcast_server.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;

    // create user
    public User createUser(User user) {
        Optional<User> savedUser = userRepository.findByUsername(user.getUsername());
        if(savedUser.isPresent()) {
            throw new UserExistsException(UserExistsException.getErrorMessageWithUsername(user.getUsername()));
        }
        return userRepository.save(user);
    }
}
