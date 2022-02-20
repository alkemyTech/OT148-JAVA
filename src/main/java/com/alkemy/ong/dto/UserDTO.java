package com.alkemy.ong.dto;

import com.alkemy.ong.repository.model.RoleModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO implements Serializable {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private String password;
    private String photo;
    private LocalDateTime creationDate;
}
