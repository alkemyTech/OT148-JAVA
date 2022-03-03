package com.alkemy.ong.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class TestimonialCreationDTO implements Serializable {

        @NotNull
        @NotBlank
        private String name;
        @NotNull
        @NotBlank
        private String content;
}
