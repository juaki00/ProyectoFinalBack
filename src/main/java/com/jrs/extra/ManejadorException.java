package com.jrs.extra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ManejadorException {

    @ExceptionHandler(AutenticacionException.class)
    public ResponseEntity<String> handleAuthenticationException( AutenticacionException ex) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleAuthenticationException( NotFoundException ex) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}