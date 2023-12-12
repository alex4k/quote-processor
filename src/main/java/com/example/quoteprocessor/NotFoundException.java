package com.example.quoteprocessor;

public class NotFoundException  extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
