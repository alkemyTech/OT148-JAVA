package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class News {
    private Long id;
    private String name;
    private String content;
    private String image;
    private Category category;
    private LocalDateTime creationDate;
}
