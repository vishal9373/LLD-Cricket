package com.cricket.Exceptions;

public class GameEndedException extends RuntimeException{

    public GameEndedException(String message){
        super(message);
    }
}
