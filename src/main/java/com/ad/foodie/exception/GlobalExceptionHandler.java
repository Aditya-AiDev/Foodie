package com.ad.foodie.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ad.foodie.dto.ErrorResponse;

import io.jsonwebtoken.security.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@RestControllerAdvice 
public class GlobalExceptionHandler {

    @ExceptionHandler (ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ResourceNotFoundException ex){
        ErrorResponse msg=ErrorResponse.builder().message(ex.getMessage())
        .status(HttpStatus.NOT_FOUND)
        .build();

        return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);

    }

    //if password is wrong//
    @ExceptionHandler (BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex){
        ErrorResponse msg=ErrorResponse.builder()
        .message("Invalid password")
        .status(HttpStatus.BAD_REQUEST)
        .build();

        return new ResponseEntity<>(msg,HttpStatus.BAD_REQUEST);

    }

    // io.jsonwebtoken.security.SignatureException -->>The token structure and Base64 encoding are valid, but the signature does not match your secret key.
    // io.jsonwebtoken.security.MalformedKeyException --->>The token breaks the fundamental syntax or structure of a JWT.

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<?> handleSignatureException(SignatureException ex) {
        return buildErrorResponse(
            "Invalid JWT signature. The token signature does not match or was signed with a different key.",
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<?> handleMalformedJwtException(MalformedJwtException ex) {
        return buildErrorResponse(
            "Malformed JWT token. The token structure or Base64 format is invalid.",
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
        return buildErrorResponse(
            "JWT token has expired. Please log in again to receive a fresh token.",
            HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<?> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        return buildErrorResponse(
            "Unsupported JWT token.",
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildErrorResponse(
            "JWT claims string is null or empty.",
            HttpStatus.BAD_REQUEST
        );
    }

    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }

    ////
    




}
