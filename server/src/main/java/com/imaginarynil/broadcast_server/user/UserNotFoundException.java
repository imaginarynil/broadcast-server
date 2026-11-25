package com.imaginarynil.broadcast_server.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public static String getErrorMessageWithUsername(String username) {
        return "Username " + "\"" + username + "\"" + " does not exist";
    }
}
