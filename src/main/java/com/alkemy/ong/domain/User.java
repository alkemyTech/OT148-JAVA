package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class User {


    private String name;
    private String lastname;
    private String email;
    private String password;

}
