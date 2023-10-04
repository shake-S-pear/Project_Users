package com.example.users.exception;

public class UserFieldsRequiredException extends RuntimeException {

    private String message;

    public UserFieldsRequiredException(){}

    public UserFieldsRequiredException(String message){
        super();
        this.message = message;
    }

}
