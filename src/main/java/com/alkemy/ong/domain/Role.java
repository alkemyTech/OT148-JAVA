package com.alkemy.ong.domain;

import com.alkemy.ong.util.RoleName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Role {
    private Integer id;
    private RoleName roleName;
    private String description;
    private LocalDateTime creationDate;
}
