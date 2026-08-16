package com.hello.hotelservice.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("resource not found on the server");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
