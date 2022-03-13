package com.alkemy.ong.exception;

public class OperationNotPermitted extends RuntimeException {
    public OperationNotPermitted(String message) {
        super(message);
    }
}
