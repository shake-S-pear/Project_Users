package com.example.users.exception;

public class UserEmailException extends RuntimeException {

    private String message;

    public UserEmailException(){}

    public UserEmailException(String message){
        super();
        this.message = message;
    }

}
