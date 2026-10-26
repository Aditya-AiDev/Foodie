package com.ad.foodie.exception;

public class InvalidFilePathException extends RuntimeException {

    

    public InvalidFilePathException() {
        super("Invalid Upload path");
    }

    public InvalidFilePathException(String message) {
        super(message);
    }
    

}
