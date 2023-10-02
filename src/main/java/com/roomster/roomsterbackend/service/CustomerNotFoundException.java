package com.roomster.roomsterbackend.service;

public class CustomerNotFoundException extends Throwable {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
