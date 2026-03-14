package com.example.backend;

public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
