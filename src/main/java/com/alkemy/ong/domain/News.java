package com.alkemy.ong.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

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
