package com.example.upiparser.exception;

public class InvalidSMSFormatException extends RuntimeException {
    public InvalidSMSFormatException(String message) {
        super(message);
    }
}
