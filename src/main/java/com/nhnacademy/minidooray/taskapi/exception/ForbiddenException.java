package com.nhnacademy.minidooray.taskapi.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException{
    private final String message;
    public ForbiddenException(String message) {
        super(message);
        this.message = message;
    }
}
