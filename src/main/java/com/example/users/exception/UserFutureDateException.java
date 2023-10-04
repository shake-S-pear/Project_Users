package com.example.users.exception;

public class UserFutureDateException extends RuntimeException {

    public UserFutureDateException(int userAge, int currentYear) {
        super("User's year of birth: " + userAge + " is grater than current year: " + currentYear);
    }

}
