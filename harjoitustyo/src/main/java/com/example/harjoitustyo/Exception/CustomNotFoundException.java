package com.example.harjoitustyo.Exception;

public class CustomNotFoundException extends RuntimeException {
    
    public CustomNotFoundException(String message) {
        super(message);
    }

}