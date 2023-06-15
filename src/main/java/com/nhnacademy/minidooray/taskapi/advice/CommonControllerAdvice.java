package com.nhnacademy.minidooray.taskapi.advice;

import com.nhnacademy.minidooray.taskapi.domain.response.Error;
import com.nhnacademy.minidooray.taskapi.exception.ForbiddenException;
import com.nhnacademy.minidooray.taskapi.exception.NotFoundException;
import com.nhnacademy.minidooray.taskapi.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

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
                .status(e.getRawStatusCode())
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Error> forbidden(ForbiddenException e, HttpServletRequest req) {
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
                .status(e.getRawStatusCode())
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(HttpServerErrorException.InternalServerError.class)
    public ResponseEntity<Error> internalError(HttpServerErrorException.InternalServerError e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(e.getRawStatusCode())
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(ValidationFailedException.class)
    public ResponseEntity<Error> validationFail(ValidationFailedException e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(400)
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpClientErrorException.MethodNotAllowed.class)
    public ResponseEntity<Error> notAllowed(HttpClientErrorException.MethodNotAllowed e, HttpServletRequest req) {
        Error error = Error.builder()
                .timeStamp(LocalDateTime.now())
                .status(e.getRawStatusCode())
                .error(e.getMessage())
                .path(req.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(error);
    }


}
