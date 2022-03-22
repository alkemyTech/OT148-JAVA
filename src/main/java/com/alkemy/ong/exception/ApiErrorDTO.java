package com.alkemy.ong.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiErrorDTO {
    private String code;
    private String message;
}
