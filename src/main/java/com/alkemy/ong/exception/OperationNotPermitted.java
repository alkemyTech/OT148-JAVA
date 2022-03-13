package com.alkemy.ong.exception;

public class OperationNotPermittedException extends RuntimeException {
    public OperationNotPermitted(String message) {
        super(message);
    }
}
