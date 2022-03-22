package com.alkemy.ong.exception;

public class OngRequestException extends RuntimeException {
    public OngRequestException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
