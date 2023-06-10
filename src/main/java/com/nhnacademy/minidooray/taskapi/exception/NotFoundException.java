package com.nhnacademy.minidooray.taskapi.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    private final String message;
    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }
}