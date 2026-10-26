package com.ad.foodie.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder 
public class ErrorResponse {

    private String message;
    private HttpStatus status;

    //AllArgConstructor
    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }


}
