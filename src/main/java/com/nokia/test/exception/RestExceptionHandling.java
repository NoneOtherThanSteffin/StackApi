package com.nokia.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandling {

    @ExceptionHandler(value = StackEmptyException.class)
    public ResponseEntity<ExceptionResponse> stackEmptyException(StackEmptyException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = StackFullException.class)
    public ResponseEntity<ExceptionResponse> stackFullException(StackFullException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INSUFFICIENT_STORAGE);
    }

    @ExceptionHandler(value = StackException.class)
    public ResponseEntity<ExceptionResponse> stackException(StackException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
