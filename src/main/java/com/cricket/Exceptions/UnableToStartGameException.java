package com.cricket.Exceptions;

public class UnableToStartGameException extends RuntimeException{

    public UnableToStartGameException(String message){
        super(message);
    }
}
