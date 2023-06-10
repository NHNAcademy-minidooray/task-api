package com.nhnacademy.minidooray.taskapi.advice;

import com.nhnacademy.minidooray.taskapi.domain.Error;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CommonControllerAdvice {
    @InitBinder
    void intBinder(WebDataBinder webDataBinder){
        webDataBinder.initDirectFieldAccess();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Error> notFound(NotFoundException e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(404)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<Error> notFound(HttpClientErrorException.NotFound e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(404)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Error> forbidden(NotFoundException e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(403)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<Error> baseForbidden(HttpClientErrorException.Forbidden e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(403)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }


}