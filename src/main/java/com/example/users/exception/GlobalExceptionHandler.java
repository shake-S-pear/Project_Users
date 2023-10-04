package com.example.users.exception;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value(value = "${data.exception.messageUserAlreadyExists}")
    private String messageUserAlreadyExists;
    @Value(value = "${data.exception.messageUserFieldsRequiredException}")
    private String messageUserFieldsRequiredException;
    @Value(value = "${data.exception.messageUserEmailException}")
    private String messageUserEmailException;


    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<String > userAlreadyExistsException(UserAlreadyExistsException userAlreadyExistsException){
        return new ResponseEntity <String>(messageUserAlreadyExists, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserYoungerThanLimitYearsException.class)
    public ResponseEntity<String > userYoungerThan18Exception(UserYoungerThanLimitYearsException userYoungerThanLimitYearsException){
        return new ResponseEntity <String>(userYoungerThanLimitYearsException.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<String > userNotFoundException(UserNotFoundException userNotFoundException){
        return new ResponseEntity <String>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UserUnSupportedFieldPatchException.class)
    public ResponseEntity<String > userUnSupportedFieldPatchException(UserUnSupportedFieldPatchException userUnSupportedFieldPatchException){
        return new ResponseEntity <String>(userUnSupportedFieldPatchException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = FindUsersByAgeBetweenException.class)
    public ResponseEntity<String > findUsersByAgeBetweenException(FindUsersByAgeBetweenException findUsersByAgeBetweenException){
        return new ResponseEntity <String>(findUsersByAgeBetweenException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserFieldsRequiredException.class)
    public ResponseEntity<String > userFieldsRequiredException(UserFieldsRequiredException userFieldsRequiredException){
        return new ResponseEntity <String>(messageUserFieldsRequiredException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = UserEmailException.class)
    public ResponseEntity<String > userEmailException(UserEmailException userEmailException){
        return new ResponseEntity <String>(messageUserEmailException, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = UserFutureDateException.class)
    public ResponseEntity<String > userFutureDateException(UserFutureDateException userFutureDateException){
        return new ResponseEntity <String>(userFutureDateException.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

}
