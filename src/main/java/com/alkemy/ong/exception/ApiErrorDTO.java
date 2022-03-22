package com.alkemy.ong.exception;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApiErrorDTO {
    private String code;
    private String message;
    private List<String> status;
}
