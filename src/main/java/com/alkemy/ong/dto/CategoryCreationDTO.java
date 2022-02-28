package com.alkemy.ong.dto;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryCreationDTO implements Serializable {

    @NotNull
    @NotBlank
    private String name;
    private String description;
    private String image;
}
