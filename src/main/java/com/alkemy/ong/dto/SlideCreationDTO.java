package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SlideCreationDTO {
    @NotNull
    @NotBlank
    private String image;
    @NotNull
    @NotBlank
    private String text;
    private Integer order;
}
