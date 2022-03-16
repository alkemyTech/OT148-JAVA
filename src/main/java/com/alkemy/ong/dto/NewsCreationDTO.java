package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class NewsCreationDTO {
    private String name;
    private String content;
    private String image;
    private Long categoryId;
}
