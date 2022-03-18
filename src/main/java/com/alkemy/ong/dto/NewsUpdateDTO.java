package com.alkemy.ong.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsUpdateDTO {
    @NotBlank
    @NotNull
    private String name;
    private String content;
    private String image;
    private CategoryDTO category;
}
