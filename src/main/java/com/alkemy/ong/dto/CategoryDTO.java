package com.alkemy.ong.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime creationDate;
}
