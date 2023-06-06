package com.nhnacademy.minidooray.taskapi.exception;

import lombok.Getter;

@Getter
public class NotFoundProjectException extends RuntimeException{
    private final String message;
    public NotFoundProjectException(String message) {
        super(message);
        this.message = message;
    }
}
