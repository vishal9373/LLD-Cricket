package com.cricket.Exceptions;

public class GameValidationException extends RuntimeException{

    public GameValidationException(String message){
        super(message);
    }
}
