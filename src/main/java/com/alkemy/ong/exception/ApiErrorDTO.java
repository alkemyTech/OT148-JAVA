package com.alkemy.ong.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorDTO {
    private String code;
    private String message;
    private List<ApiErrorDetailDTO> details;
}

@Data
@NoArgsConstructor
class ApiErrorDetailDTO {
    private String field;
    private String error;
}
