package com.grit.learning.exception;

public class FileNotFoundException extends RuntimeException{
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FileNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
