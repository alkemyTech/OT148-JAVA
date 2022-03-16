package com.alkemy.ong.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialCreationDTO implements Serializable {

    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String content;
    private String image;
}
