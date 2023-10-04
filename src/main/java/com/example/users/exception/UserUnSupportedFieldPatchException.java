package com.example.users.exception;

import java.util.Set;

public class UserUnSupportedFieldPatchException extends RuntimeException {

    public UserUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allowed.");
    }

}
