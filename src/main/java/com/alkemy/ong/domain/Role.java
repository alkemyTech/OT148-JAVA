package com.alkemy.ong.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime creationDate;
}
