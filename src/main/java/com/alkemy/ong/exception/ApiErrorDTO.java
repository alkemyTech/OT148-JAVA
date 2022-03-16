package com.alkemy.ong.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiErrorDTO {
    private HttpStatus code;
    private String message;
}
