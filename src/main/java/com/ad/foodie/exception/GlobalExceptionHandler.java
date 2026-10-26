package com.ad.foodie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ad.foodie.dto.ErrorResponse;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException ex){
        ErrorResponse msg=ErrorResponse.builder().message(ex.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .build();

        return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);

    }

}
