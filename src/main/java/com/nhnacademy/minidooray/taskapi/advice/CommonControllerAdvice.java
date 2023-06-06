package com.nhnacademy.minidooray.taskapi.advice;

import com.nhnacademy.minidooray.taskapi.domain.Error;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundProjectException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundProjectMemberException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonControllerAdvice {
    @InitBinder
    void intBinder(WebDataBinder webDataBinder){
        webDataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(NotFoundProjectException.class)
    public ResponseEntity<Error> notFoundProject(NotFoundProjectException e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(404)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(NotFoundProjectMemberException.class)
    public ResponseEntity<Error> notFoundProject(NotFoundProjectMemberException e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(404)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
