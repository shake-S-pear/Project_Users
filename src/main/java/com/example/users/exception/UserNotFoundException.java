package com.example.users.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int id) {
        super("Book id: " + id + " not found.");
    }

}
