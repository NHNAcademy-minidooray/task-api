package com.nhnacademy.minidooray.taskapi.exception;


import lombok.Getter;

@Getter
public class NotFoundProjectMemberException extends RuntimeException {
    private final String message;
    public NotFoundProjectMemberException(String message) {
        super(message);
        this.message = message;
    }
}
