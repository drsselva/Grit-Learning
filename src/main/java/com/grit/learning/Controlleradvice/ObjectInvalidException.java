package com.grit.learning.Controlleradvice;

public class ObjectInvalidException extends RuntimeException {
	public ObjectInvalidException(String message) {
        super(message);
    }

    public ObjectInvalidException() {
    }
}
