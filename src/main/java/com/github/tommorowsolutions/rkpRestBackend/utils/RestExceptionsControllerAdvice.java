package com.github.tommorowsolutions.rkpRestBackend.utils;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionsControllerAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity exceptionEntityNotFoundHandler(){
        return  ResponseEntity
                .badRequest()
                .body("Сущность с таким id не найдена");
    }
    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity exceptionEntityExistsHandler(){
        return  ResponseEntity
                .badRequest()
                .body("Сущность с таким id уже существует");
    }

}
