package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewsUpdateDTO {
    private String name;
    private String content;
    private String image;
    private CategoryDTO category;
}
