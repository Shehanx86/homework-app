package com.homework.app.exception;

public class InvalidTokenException extends Exception{
    public InvalidTokenException(String message){
        super(message);
    }
}