package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsDTO {
    private Long id;
    private String name;
    private String content;
    private String image;
    private CategoryDTO category;
    private LocalDateTime creationDate;
}
