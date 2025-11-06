package com.example.harjoitustyo.Exception;

public class CustomBadRequestException extends RuntimeException{

    public CustomBadRequestException(String message) {
        super(message);
    }

}
