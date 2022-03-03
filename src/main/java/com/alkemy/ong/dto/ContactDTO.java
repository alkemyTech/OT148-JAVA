package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContactDTO implements Serializable {
    @NotNull
    @NotBlank
    private String name;
    private Integer phone;
    @NotNull
    @NotBlank
    private String email;
    private String message;
}
