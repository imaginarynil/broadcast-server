package com.imaginarynil.broadcast_server.user;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserExistsException extends RuntimeException {
    public UserExistsException(String message) {
        super(message);
    }

    public static String getErrorMessageWithUsername(String username) {
        return "Username " + "\"" + username + "\"" + " already exists";
    }
}
