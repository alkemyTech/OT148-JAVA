package com.alkemy.ong.exception;

public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermittedException(String message) {
        super(message);
    }
}
