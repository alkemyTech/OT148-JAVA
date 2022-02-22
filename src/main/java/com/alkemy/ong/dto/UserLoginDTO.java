package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class UserLoginDTO {

    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String password;
}
