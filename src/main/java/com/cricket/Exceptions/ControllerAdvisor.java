package com.cricket.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GameValidationException.class)
    public ResponseEntity<String> handleGameValidation(GameValidationException gameValidationException){
        return new ResponseEntity<String>(gameValidationException.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException){
        return new ResponseEntity<String>("No Such Game Present", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnableToStartGameException.class)
    public ResponseEntity<String> handleUnableToStart(UnableToStartGameException unableToStartGameException){
        return new ResponseEntity<String>(unableToStartGameException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameEndedException.class)
    public ResponseEntity<String> handleGameEnded(GameEndedException gameEndedException){
        return new ResponseEntity<String>(gameEndedException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
