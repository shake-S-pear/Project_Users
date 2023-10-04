package com.example.users.exception;

public class UserYoungerThanLimitYearsException extends RuntimeException{

    public UserYoungerThanLimitYearsException(int id) {
        super("User must be over: " + id + " year old");
    }

}
