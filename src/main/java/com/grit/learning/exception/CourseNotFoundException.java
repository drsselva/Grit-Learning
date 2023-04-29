package com.grit.learning.exception;

public class CourseNotFoundException extends RuntimeException{
    String message;
    public CourseNotFoundException(String message){
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
