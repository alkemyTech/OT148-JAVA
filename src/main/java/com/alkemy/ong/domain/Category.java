package com.alkemy.ong.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Category {
    private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime creationDate;
}
