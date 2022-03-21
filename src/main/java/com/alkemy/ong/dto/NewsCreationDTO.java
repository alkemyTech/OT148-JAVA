package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewsCreationDTO {
    @NotNull
    @NotBlank
    private String name;
    private String content;
    private String image;
    private Long categoryId;
}
